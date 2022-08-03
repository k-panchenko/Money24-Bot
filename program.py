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

from client.money24_client import Money24Client
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

money24_client = Money24Client()


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
    rates = json.loads(await redis.get(redis_keys.RATES))
    buy_rate = next(filter(lambda rate: rate['type'] == 'buy', rates))
    sell_rate = next(filter(lambda rate: rate['type'] == 'sal', rates))
    text = '\n'.join(['Доллар США 🇺🇸', '', f'Покупка: {buy_rate["rate"]}', f'Продажа: {sell_rate["rate"]}'])
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
    rates = await redis.get(redis_keys.RATES)
    old_rates = json.loads(rates) if rates else None
    new_rates = await sync_rate()
    if not old_rates or old_rates == new_rates:
        return
    await notify_about_new_rates(old_rates, new_rates)


async def sync_rate():
    rates = await money24_client.get_rates()
    result = rates['result']
    usd_rates = [rate for rate in result if rate['currCode'] == currency.USD]
    await redis.set(redis_keys.RATES, json.dumps(usd_rates))
    return usd_rates


async def notify_about_new_rates(old_rates, new_rates):
    old_buy_rate = next(filter(lambda rate: rate['type'] == 'buy', old_rates))
    old_sell_rate = next(filter(lambda rate: rate['type'] == 'sal', old_rates))

    new_buy_rate = next(filter(lambda rate: rate['type'] == 'buy', new_rates))
    new_sell_rate = next(filter(lambda rate: rate['type'] == 'sal', new_rates))

    buy_diff = new_buy_rate['rate'] - old_buy_rate['rate']
    sell_diff = new_sell_rate['rate'] - old_sell_rate['rate']

    text = '\n'.join([
        'Курс изменился❗',
        '\n'
        'Доллар США 🇺🇸',
        '\n'
        f'Покупка: {new_buy_rate["rate"]} {diff_to_move(buy_diff)}',
        f'Продажа: {new_sell_rate["rate"]} {diff_to_move(sell_diff)}'
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
        return f'🔺🔴 {number}'
    elif number < 0:
        return f'🔻🟢 {abs(number)}'
    else:
        return ''


if __name__ == '__main__':
    start_polling(dp)
