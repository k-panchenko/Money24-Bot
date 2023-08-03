package com.ua.money24.service.observer;

import com.ua.money24.model.response.ExecAsPublicResponse;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class TelegramRateObserver implements RateObserver {
    private final AbsSender absSender;

    public TelegramRateObserver(AbsSender absSender) {
        this.absSender = absSender;
    }

    @Override
    public void observe(ExecAsPublicResponse.Result.Rate prevRate, ExecAsPublicResponse.Result.Rate newRate) {

    }
}
