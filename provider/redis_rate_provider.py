import json
from typing import Optional

from aioredis import Redis

from provider.base.rate_provider import RateProvider
from statics import redis_keys


class RedisRateProvider(RateProvider):
    def __init__(self, redis: Redis):
        self._redis = redis

    async def get_rates(self) -> Optional[dict]:
        result = await self._redis.get(redis_keys.RATES)
        return json.loads(result) if result else None

    async def save_rates(self, rates: dict):
        await self._redis.set(redis_keys.RATES, json.dumps(rates))
