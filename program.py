import asyncio
import json
import logging
from os import environ as env

import aiocron
from aiogram import Dispatcher, Bot, types
from aiogram.dispatcher import filters
from aiogram.utils import exceptions, markdown
from aiogram.utils.executor import start_polling
from aioredis import Redis
from statics.types import *

from client.money24_client import Money24Client
from provider.money24_rate_provider import Money24RateProvider
from provider.redis_rate_provider import RedisRateProvider
from statics import menu, currency, redis_keys, url

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


@dp.message_handler(commands='start')
async def start_command(message: types.Message):
    await message.answer(f'''Добро пожаловать!
Курс в этом боте мониторится по адресу:

Супермаркет «Silverland»
Мукачево, ул. Матросова, 19
Тел.: +380665506700
График работы: 09:00 - 20:00
Обмен валют {markdown.link('Money24', url.MONEY24_URL)}''', types.ParseMode.MARKDOWN)
    await message.answer_location(48.43761899471841, 22.751754355098168,
                                  reply_markup=await create_keyboard(message.from_user.id))


@dp.message_handler(filters.Text(equals=menu.GET_RATES))
async def get_rates_handler(message: types.Message):
    rates = await redis_rate_provider.get_rates()
    buy_rate = rates[currency.USD][BUY]
    sell_rate = rates[currency.USD][SELL]
    text = '\n'.join(['Доллар США 🇺🇸', '', f'Покупка: {buy_rate}', f'Продажа: {sell_rate}'])
    await message.answer(text)


@dp.message_handler(filters.Text(equals=menu.SUB))
async def sub_handler(message: types.Message):
    await redis.sadd(redis_keys.SUBS, message.from_user.id)
    await message.answer('Как только курс изменится, мы дадим вам знать 😌',
                         reply_markup=await create_keyboard(message.from_user.id))


@dp.message_handler(filters.Text(equals=menu.UNSUB))
async def unsub_handler(message: types.Message):
    await redis.srem(redis_keys.SUBS, message.from_user.id)
    await message.answer('Вы отписались от рассылки 😔',
                         reply_markup=await create_keyboard(message.from_user.id))


@aiocron.crontab('* * * * *')
async def do_work():
    curr_rates = await redis_rate_provider.get_rates()
    new_rates = await money24_rate_provider.get_rates()
    up_to_date = curr_rates[currency.USD] != new_rates[currency.USD]
    if not curr_rates or up_to_date:
        await redis_rate_provider.save_rates(new_rates)
    if not curr_rates:
        return
    if not up_to_date:
        return
    await notify_about_new_rates(curr_rates, new_rates)


async def notify_about_new_rates(curr_rates, new_rates):
    buy_diff = round(new_rates[currency.USD][BUY] - curr_rates[currency.USD][BUY], 2)
    sell_diff = round(new_rates[currency.USD][SELL] - curr_rates[currency.USD][SELL], 2)

    text = '\n'.join([
        'Курс изменился❗',
        '\n'
        'Доллар США 🇺🇸',
        '\n'
        f'Покупка: {new_rates[currency.USD][BUY]} {diff_to_move(buy_diff)}',
        f'Продажа: {new_rates[currency.USD][SELL]} {diff_to_move(sell_diff)}'
    ])

    for member in await redis.smembers(redis_keys.SUBS):
        try:
            await bot.send_message(member, text)
            await asyncio.sleep(.05)
        except exceptions.BadRequest as ex:
            logger.warning(f'Bad request while sending message to {member}: {ex}')


async def create_keyboard(user: int):
    buttons = [types.KeyboardButton(menu.GET_RATES)]
    is_member = await redis.sismember(redis_keys.SUBS, user)
    buttons.append(types.KeyboardButton(menu.UNSUB if is_member else menu.SUB))
    return types.ReplyKeyboardMarkup(resize_keyboard=True, row_width=1).add(*buttons)


def diff_to_move(number: float):
    if number > 0:
        return f'🆘 ➕{number}'
    elif number < 0:
        return f'💹 ➖{abs(number)}'
    else:
        return ''


if __name__ == '__main__':
    start_polling(dp)
