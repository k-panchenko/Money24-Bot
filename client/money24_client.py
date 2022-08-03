import aiohttp

from statics import url


class Money24Client:
    _rates_api = '/00000000-0000-0000-0000-000000000000/api/exec-as-public'
    _rates_body = {"execType": "get", "method": "/rates/department-and-region/2/3", "jsonData": {}}

    async def get_rates(self) -> dict:
        async with aiohttp.ClientSession(url.MONEY24_URL) as session:
            async with session.post(self._rates_api, data=self._rates_body) as response:
                return await response.json()
