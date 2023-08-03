package com.ua.money24.service.calculator;

import com.ua.money24.model.RateDifference;
import com.ua.money24.model.response.ExecAsPublicResponse;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class RateDifferenceCalculatorImpl implements RateDifferenceCalculator {
    private final double decimalPlaces;

    public RateDifferenceCalculatorImpl(@Value("${application.rate.decimal-places:2}") double decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    public RateDifference calculate(ExecAsPublicResponse.Result.Rate prevRate, ExecAsPublicResponse.Result.Rate newRate) {
        return new RateDifference(
                BigDecimal.valueOf(prevRate.r)
        )
    }
}
