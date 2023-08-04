package com.ua.money24.service.calculator;

import com.ua.money24.model.Rate;
import com.ua.money24.model.RateDifference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class RateDifferenceCalculatorImpl implements RateDifferenceCalculator {
    private final int decimalPlaces;

    public RateDifferenceCalculatorImpl(@Value("${application.rate.decimal-places:2}") int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }


    @Override
    public RateDifference calculate(Rate prevRate, Rate newRate) {
        return new RateDifference(
                substract(prevRate.buyRate(), newRate.buyRate()),
                substract(prevRate.sellRate(), newRate.sellRate())
        );
    }

    private double substract(double prevRate, double newRate) {
        return BigDecimal.valueOf(newRate)
                .subtract(BigDecimal.valueOf(prevRate))
                .setScale(decimalPlaces, RoundingMode.HALF_EVEN)
                .doubleValue();
    }
}
