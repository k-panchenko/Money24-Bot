package com.ua.money24.service.observer;

import com.ua.money24.model.Rate;

public interface RateObserver {
    void observe(Rate prevRate, Rate newRate);
}
