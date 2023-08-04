package com.ua.money24.service.converter;

import com.ua.money24.model.Rate;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RateToEntityConverter implements Function<Rate, com.ua.money24.entity.Rate> {
    @Override
    public com.ua.money24.entity.Rate apply(Rate rate) {
        return new com.ua.money24.entity.Rate(
                rate.id(),
                rate.regionId(),
                rate.currencyCode(),
                rate.currencyId(),
                rate.buyRate(),
                rate.sellRate()
        );
    }
}
