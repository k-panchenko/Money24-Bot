package com.ua.money24.service.calculator;

import com.ua.money24.model.RateDifference;
import com.ua.money24.model.response.ExecAsPublicResponse;

public interface RateDifferenceCalculator {
    RateDifference calculate(ExecAsPublicResponse.Result.Rate prevRate, ExecAsPublicResponse.Result.Rate newRate);
}
