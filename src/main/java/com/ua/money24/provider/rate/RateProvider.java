package com.ua.money24.provider.rate;

import com.ua.money24.model.Rate;

import java.util.List;

public interface RateProvider {
    List<Rate> getRatesInRegion(int regionId);
}
