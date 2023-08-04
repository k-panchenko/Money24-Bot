package com.ua.money24.service.provider.subscriber;

import com.ua.money24.model.Subscriber;

import java.util.List;

public interface SubscriberProvider {
    List<Subscriber> getSubscribersByRegionAndCurrency(Integer regionId, Integer currencyId);
}
