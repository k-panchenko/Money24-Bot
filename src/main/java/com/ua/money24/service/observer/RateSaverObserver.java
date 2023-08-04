package com.ua.money24.service.observer;

import com.ua.money24.model.Rate;
import com.ua.money24.service.repository.RateRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RateSaverObserver implements RateObserver {
    private final RateRepository rateRepository;
    private final Function<Rate, com.ua.money24.entity.Rate> rateFunction;

    public RateSaverObserver(RateRepository rateRepository, Function<Rate, com.ua.money24.entity.Rate> rateFunction) {
        this.rateRepository = rateRepository;
        this.rateFunction = rateFunction;
    }

    @Override
    public void observe(@Nullable Rate prevRate, Rate newRate) {
        var entity = rateFunction.apply(newRate);
        rateRepository.save(entity);
    }
}
