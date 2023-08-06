package com.ua.money24.service.converter;

import com.ua.money24.entity.Region;
import com.ua.money24.model.Subscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SubscriberToEntityConverter implements Function<Subscriber, com.ua.money24.entity.Subscriber> {
    private final Integer regionId;

    public SubscriberToEntityConverter(@Value("${application.region}") Integer regionId) {
        this.regionId = regionId;
    }

    @Override
    public com.ua.money24.entity.Subscriber apply(Subscriber subscriber) {
        return new com.ua.money24.entity.Subscriber()
                .setId(subscriber.id())
                .setRegion(new Region().setId(regionId));
    }
}
