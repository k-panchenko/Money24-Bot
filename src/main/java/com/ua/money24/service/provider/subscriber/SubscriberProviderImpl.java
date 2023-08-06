package com.ua.money24.service.provider.subscriber;

import com.ua.money24.model.Subscriber;
import com.ua.money24.service.repository.SubscriberRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class SubscriberProviderImpl implements SubscriberProvider {
    private final SubscriberRepository subscriberRepository;
    private final Function<com.ua.money24.entity.Subscriber, Subscriber> entitySubscriberFunction;

    private final Function<Subscriber, com.ua.money24.entity.Subscriber> subscriberEntityFunction;

    public SubscriberProviderImpl(SubscriberRepository subscriberRepository,
                                  Function<com.ua.money24.entity.Subscriber, Subscriber> entitySubscriberFunction,
                                  Function<Subscriber, com.ua.money24.entity.Subscriber> subscriberEntityFunction) {
        this.subscriberRepository = subscriberRepository;
        this.entitySubscriberFunction = entitySubscriberFunction;
        this.subscriberEntityFunction = subscriberEntityFunction;
    }


    @Override
    public Optional<Subscriber> findById(Long id) {
        return subscriberRepository.findById(id)
                .map(entitySubscriberFunction);
    }

    @Override
    public List<Subscriber> getSubscribersByRegionAndCurrency(Integer regionId, Integer currencyId) {
        return subscriberRepository.findAllByRegionIdAndCurrenciesId(regionId, currencyId)
                .stream()
                .map(entitySubscriberFunction)
                .toList();
    }

    @Override
    public Subscriber save(Subscriber subscriber) {
        var entity = subscriberEntityFunction.apply(subscriber);
        entity = subscriberRepository.save(entity);
        return entitySubscriberFunction.apply(entity);
    }
}
