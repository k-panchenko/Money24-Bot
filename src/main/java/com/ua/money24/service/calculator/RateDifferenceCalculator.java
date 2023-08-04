package com.ua.money24.service.calculator;

import com.ua.money24.model.Rate;
import com.ua.money24.model.RateDifference;

public interface RateDifferenceCalculator {
    RateDifference calculate(Rate prevRate, Rate newRate);
}
