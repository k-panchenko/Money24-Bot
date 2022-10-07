from aioredis import Redis

from provider.base.currency_provider import CurrencyProvider
from statics import redis_keys


class RedisCurrencyProvider(CurrencyProvider):
    def __init__(self, redis: Redis):
        self._redis = redis

    async def get_currencies(self) -> list[str]:
        return await self._redis.zrangebyscore(redis_keys.CURRENCIES, '-inf', '+inf')
