import asyncio
import logging
from os import environ as env
from typing import Optional

import aiocron
from aiogram import Dispatcher, Bot, types
from aiogram.dispatcher import filters
from aiogram.utils import exceptions, markdown
from aiogram.utils.executor import start_polling
from aioredis import Redis

from client.money24_client import Money24Client
from provider.currency_provider import RedisCurrencyProvider
from provider.money24_rate_provider import Money24RateProvider
from provider.redis_rate_provider import RedisRateProvider
from provider.subscribers_provider import RedisSubscribersProvider
from statics import menu, url
from utils import currency_utils, keyboard_utils
from utils.callback_utils import subscribe_cd

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

BOT_TOKEN = env['BOT_TOKEN']
REDIS_HOST = env.get('REDIS_HOST', 'localhost')
REDIS_PORT = env.get('REDIS_PORT', 6379)
REDIS_DB = env.get('REDIS_DB', 3)

bot = Bot(BOT_TOKEN)
dp = Dispatcher(bot)

redis = Redis(host=REDIS_HOST, port=REDIS_PORT, db=REDIS_DB, decode_responses=True)

money24_rate_provider = Money24RateProvider(Money24Client())
redis_rate_provider = RedisRateProvider(redis)
currency_provider = RedisCurrencyProvider(redis)
subscribers_provider = RedisSubscribersProvider(redis)


@dp.message_handler(commands='start')
async def start_command(message: types.Message):
    await message.answer(f'''
Добро пожаловать!
Курс в этом боте мониторится по обменке {markdown.link('Money24', url.MONEY24_URL)}
''', types.ParseMode.MARKDOWN, reply_markup=keyboard_utils.create_menu())


@dp.message_handler(filters.Text(equals=menu.GET_RATES))
async def get_rates_handler(message: types.Message):
    rates = await redis_rate_provider.get_rates()
    await message.answer(currency_utils.rates_to_text(rates))


@dp.message_handler(filters.Text(equals=menu.SUB))
async def sub_handler(message: types.Message):
    user_id = message.from_user.id
    markup = await sub_currencies_keyboard(user_id)
    await message.answer('Выберите валюты для подписки:', reply_markup=markup)


@dp.callback_query_handler(subscribe_cd.filter())
async def sub_currency_handler(query: types.CallbackQuery):
    user_id = query.from_user.id
    _, currency = subscribe_cd.parse(query.data).values()
    await change_subscription_status(user_id, currency)
    await query.answer('Изменения сохранены')
    await query.message.edit_reply_markup(await sub_currencies_keyboard(user_id))


@aiocron.crontab('* * * * *')
async def do_work():
    curr_rates = await redis_rate_provider.get_rates()
    new_rates = await money24_rate_provider.get_rates()
    if not curr_rates:
        await redis_rate_provider.save_rates(new_rates)
        return
    await update_rates(curr_rates, new_rates)


async def update_rates(curr_rates: dict, new_rates: dict):
    for rate in new_rates:
        curr_rate = curr_rates[rate]
        new_rate = new_rates[rate]
        if curr_rate == new_rate:
            continue
        await redis_rate_provider.save_rates(new_rates)  # TODO: save only one maybe
        await notify_about_new_rates(rate, curr_rate, new_rate)


async def notify_about_new_rates(currency: str, curr_rates: dict, new_rates: dict):
    text = currency_utils.create_text_diff(currency, curr_rates, new_rates)
    for subscriber in await subscribers_provider.get_subscribers(currency):
        try:
            await bot.send_message(subscriber, text)
            await asyncio.sleep(.05)
        except exceptions.TelegramAPIError as ex:
            logger.warning(f'Bad request while sending message to {subscriber}: {ex}')


async def change_subscription_status(user: str, currency: str):
    is_subscribed = await subscribers_provider.is_subscribed(user, currency)
    if is_subscribed:
        await subscribers_provider.remove_subscriber(user, currency)
    else:
        await subscribers_provider.add_subscriber(user, currency)


async def sub_currencies_keyboard(user, currencies: Optional[list[str]] = None) -> types.InlineKeyboardMarkup:
    if not currencies:
        currencies = await currency_provider.get_currencies()
    return keyboard_utils.create_sub_menu({
        currency: await subscribers_provider.is_subscribed(user, currency) for currency in currencies
    })


if __name__ == '__main__':
    start_polling(dp)
