package com.ua.money24.service.provider.subscriber;

import com.ua.money24.model.Subscriber;

import java.util.List;
import java.util.Optional;

public interface SubscriberProvider {
    Optional<Subscriber> findById(Long id);
    List<Subscriber> getSubscribersByRegionAndCurrency(Integer regionId, Integer currencyId);

    Subscriber save(Subscriber subscriber);
}
