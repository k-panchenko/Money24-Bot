from abc import ABC, abstractmethod


class CurrencyProvider(ABC):
    @abstractmethod
    async def get_currencies(self) -> list[str]:
        pass
