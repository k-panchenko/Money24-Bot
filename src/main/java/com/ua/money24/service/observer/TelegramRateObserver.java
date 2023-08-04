package com.ua.money24.service.observer;

import com.ua.money24.model.Rate;
import com.ua.money24.service.calculator.RateDifferenceCalculator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class TelegramRateObserver implements RateObserver {
    private final AbsSender absSender;
    private final RateDifferenceCalculator rateDifferenceCalculator;

    public TelegramRateObserver(AbsSender absSender, RateDifferenceCalculator rateDifferenceCalculator) {
        this.absSender = absSender;
        this.rateDifferenceCalculator = rateDifferenceCalculator;
    }


    @Override
    public void observe(Rate prevRate, Rate newRate) {

    }
}
