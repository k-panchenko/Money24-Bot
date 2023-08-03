package com.ua.money24.service.publisher;

import com.ua.money24.model.response.ExecAsPublicResponse;

public interface RatePublisher {
    void publish(ExecAsPublicResponse.Result.Rate prevRate, ExecAsPublicResponse.Result.Rate newRate);
}
