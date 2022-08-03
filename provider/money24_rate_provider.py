from typing import Optional

from client.money24_client import Money24Client
from provider.base.rate_provider import RateProvider
from statics import types


class Money24RateProvider(RateProvider):
    def __init__(self, money24_client: Money24Client):
        self._money24_client = money24_client

    async def get_rates(self) -> Optional[dict]:
        rates = await self._money24_client.get_rates()
        result = {}
        for rate in rates['result']:
            currency = str(rate['currCode']).upper()
            typed_rate = result.setdefault(currency, {})
            typed_rate[types.BUY if rate['type'] == 'buy' else types.SELL] = rate['rate']
            rate[currency] = typed_rate
        return result

    async def save_rates(self, rates: dict):
        raise NotImplementedError()


