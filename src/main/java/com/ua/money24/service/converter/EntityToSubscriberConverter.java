package com.ua.money24.service.converter;

import com.ua.money24.entity.Subscriber;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EntityToSubscriberConverter implements Function<Subscriber, com.ua.money24.model.Subscriber> {
    @Override
    public com.ua.money24.model.Subscriber apply(Subscriber subscriber) {
        return new com.ua.money24.model.Subscriber(subscriber.getId(), subscriber.getRegion().getId());
    }
}
