package com.ua.money24.service.provider.subscriber;

import com.ua.money24.model.Subscriber;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriberProviderImpl implements SubscriberProvider {
    @Override
    public List<Subscriber> getSubscribersByRegionAndCurrency(Integer regionId, Integer currencyId) {
        return null;
    }
}
