package com.ua.money24.provider.rate;

import com.ua.money24.model.Rate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InternalRateProvider implements RateProvider {
    @Override
    public List<Rate> getRatesInRegion(int regionId) {
        return null;
    }
}
