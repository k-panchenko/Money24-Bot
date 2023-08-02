package com.ua.money24.scheduler;

import com.ua.money24.model.request.ExecAsPublicRequest;
import com.ua.money24.provider.rate.RateProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateSynchronizer {
    private final RateProvider money24RateProvider;
    private final RateProvider internalRateProvider;

    public RateSynchronizer(RateProvider money24RateProvider, RateProvider internalRateProvider) {
        this.money24RateProvider = money24RateProvider;
        this.internalRateProvider = internalRateProvider;
    }

    @Scheduled(fixedRate = 5000)
    public void syncRates() {

    }
}