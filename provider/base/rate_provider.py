from abc import ABC, abstractmethod
from typing import Optional


class RateProvider(ABC):

    @abstractmethod
    async def get_rates(self) -> Optional[dict]:
        pass

    @abstractmethod
    async def save_rates(self, rates: dict):
        pass
