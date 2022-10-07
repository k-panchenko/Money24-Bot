from abc import ABC, abstractmethod


class SubscribersProvider(ABC):
    @abstractmethod
    async def get_subscribers(self, currency: str) -> list[str]:
        pass

    @abstractmethod
    async def is_subscribed(self, user: str, currency: str) -> bool:
        pass

    @abstractmethod
    async def add_subscriber(self, user: str, currency: str):
        pass

    @abstractmethod
    async def remove_subscriber(self, user: str, currency: str):
        pass
