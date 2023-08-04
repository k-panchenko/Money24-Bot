package com.ua.money24.entity;

import java.io.Serializable;

public record SubscriberCurrencyId(Integer subscriberId, Integer currencyId) implements Serializable {
}
