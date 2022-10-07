from statics.types import BUY, SELL

currency_dict = {
    'USD': 'Доллар США 🇺🇸',
    'EUR': 'Евро 🇪🇺',
    'CZK': 'Чешская крона 🇨🇿',
    'PLN': 'Польский злотый 🇵🇱',
    'HUF': 'Венгерский форинт 🇭🇺',
    'GBP': 'Фунт стерлингов 🇬🇧',
    'CHF': 'Швейцарский франк 🇨🇭',
    'LEI': 'Румынский лей 🇷🇴'
}


def diff_to_move(number: float, rtype: str) -> str:
    if number == 0:
        return ''
    signal = '💹' if rtype == BUY and number > 0 or rtype == SELL and number < 0 else '🆘'
    sign = '➕' if number > 0 else '➖'

    return f'{signal} {sign}{abs(number)}'


def currency_to_text(currency: str) -> str:
    return currency_dict.get(currency, 'Unknown currency 🏳️')


def rates_to_text(rates: dict) -> str:
    return '\n'.join([f'''{currency_to_text(rate)}️
Покупка: {rates[rate][BUY]}
Продажа: {rates[rate][SELL]}
''' for rate in rates if rate in currency_dict])


def create_text_diff(currency: str, curr_rate: dict, new_rate: dict) -> str:
    buy_diff = round(new_rate[BUY] - curr_rate[BUY], 2)
    sell_diff = round(new_rate[SELL] - curr_rate[SELL], 2)

    return '\n'.join([
        f'{currency_to_text(currency)} Курс изменился❗',
        '\n'
        f'Покупка: {new_rate[BUY]} {diff_to_move(buy_diff, BUY)}',
        f'Продажа: {new_rate[SELL]} {diff_to_move(sell_diff, SELL)}'
    ])
