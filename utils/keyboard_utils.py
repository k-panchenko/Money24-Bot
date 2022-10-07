from aiogram import types

from statics import menu
from utils import callback_utils, currency_utils


def create_menu():
    return types.ReplyKeyboardMarkup(resize_keyboard=True, row_width=1).add(*[
        types.KeyboardButton(menu.GET_RATES),
        types.KeyboardButton(menu.SUB)
    ])


def create_sub_menu(currencies_to_sub: dict):
    return types.InlineKeyboardMarkup(row_width=1).add(*[
        types.InlineKeyboardButton(
            ' '.join([
                currency_utils.currency_to_text(currency),
                "🔔" if currencies_to_sub[currency] else ""
            ]),
            callback_data=callback_utils.subscribe_cd.new(currency=currency)) for currency in currencies_to_sub
    ])
