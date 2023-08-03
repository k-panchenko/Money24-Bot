package com.ua.money24.service.scheduler;

import com.ua.money24.service.provider.rate.RateProvider;
import com.ua.money24.service.provider.region.RegionProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RateSynchronizer {
    private final RateProvider money24RateProvider;
    private final RateProvider internalRateProvider;
    private final RegionProvider regionProvider;

    public RateSynchronizer(RateProvider money24RateProvider, RateProvider internalRateProvider,
                            RegionProvider regionProvider) {
        this.money24RateProvider = money24RateProvider;
        this.internalRateProvider = internalRateProvider;
        this.regionProvider = regionProvider;
    }

    @Scheduled(fixedRateString = "${application.sync-rate-seconds}", timeUnit = TimeUnit.SECONDS)
    public void syncRates() {
        regionProvider.getRegions().forEach(region -> {
            var money24Rates = money24RateProvider.getRatesInRegion(region.id());
            var internalRates = internalRateProvider.getRatesInRegion(region.id());

            money24Rates.removeAll(internalRates);

            money24Rates.forEach(newRate -> internalRates.stream()
                    .filter(rate -> rate.id() == newRate.id() &&
                            rate.type().equals(newRate.type()) &&
                            rate.rate() != newRate.rate())
                    .findFirst()
                    .ifPresent(prevRate -> {

                    }));
        });
    }
}