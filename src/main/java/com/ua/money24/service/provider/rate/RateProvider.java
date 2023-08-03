package com.ua.money24.service.provider.rate;

import com.ua.money24.model.Rate;
import com.ua.money24.model.response.ExecAsPublicResponse;

import java.util.List;

public interface RateProvider {
    List<Rate> getRatesInRegion(int regionId);
}
