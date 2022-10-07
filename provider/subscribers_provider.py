from aioredis import Redis

from provider.base.subscribers_provider import SubscribersProvider
from statics import redis_keys


class RedisSubscribersProvider(SubscribersProvider):
    def __init__(self, redis: Redis):
        self._redis = redis

    async def get_subscribers(self, currency: str) -> list[str]:
        return await self._redis.smembers(redis_keys.SUBS.format(currency=currency))

    async def is_subscribed(self, user: str, currency: str) -> bool:
        return await self._redis.sismember(redis_keys.SUBS.format(currency=currency), user)

    async def add_subscriber(self, user: str, currency: str):
        return await self._redis.sadd(redis_keys.SUBS.format(currency=currency), user)

    async def remove_subscriber(self, user: str, currency: str):
        return await self._redis.srem(redis_keys.SUBS.format(currency=currency), user)
