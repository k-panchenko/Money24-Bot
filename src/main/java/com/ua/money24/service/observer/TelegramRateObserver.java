package com.ua.money24.service.observer;

import com.ua.money24.model.Rate;
import com.ua.money24.model.Subscriber;
import com.ua.money24.service.calculator.RateDifferenceCalculator;
import com.ua.money24.service.provider.subscriber.SubscriberProvider;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class TelegramRateObserver implements RateObserver {
    private final AbsSender absSender;
    private final RateDifferenceCalculator rateDifferenceCalculator;
    private final SubscriberProvider subscriberProvider;

    public TelegramRateObserver(AbsSender absSender, RateDifferenceCalculator rateDifferenceCalculator,
                                SubscriberProvider subscriberProvider) {
        this.absSender = absSender;
        this.rateDifferenceCalculator = rateDifferenceCalculator;
        this.subscriberProvider = subscriberProvider;
    }


    @Override
    public void observe(@Nullable Rate prevRate, Rate newRate) {
        if (prevRate == null) {
            return;
        }
        var diff = rateDifferenceCalculator.calculate(prevRate, newRate);
        var subscribers = subscriberProvider.getSubscribersByRegionAndCurrency(
                newRate.regionId(),
                newRate.currencyId()
        );
        for (Subscriber subscriber : subscribers) {

        }
    }
}
