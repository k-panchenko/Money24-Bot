package com.ua.money24.service.converter;

import com.ua.money24.model.Rate;
import com.ua.money24.model.response.ExecAsPublicResponse;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class RateConverter implements BiFunction<ExecAsPublicResponse.Result.Rate, ExecAsPublicResponse.Result.Rate, Rate> {
    @Override
    public Rate apply(ExecAsPublicResponse.Result.Rate buyRate, ExecAsPublicResponse.Result.Rate sellRate) {
        return new Rate(
                buyRate.id(),
                buyRate.regionID(),
                buyRate.currCode(),
                buyRate.currId(),
                buyRate.rate(),
                sellRate.rate()
        );
    }
}
