package com.ua.money24.service.observer;

import com.ua.money24.model.response.ExecAsPublicResponse;

public interface RateObserver {
    void observe(ExecAsPublicResponse.Result.Rate prevRate, ExecAsPublicResponse.Result.Rate newRate);
}
