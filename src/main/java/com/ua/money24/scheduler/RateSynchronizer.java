package com.ua.money24.scheduler;

import com.ua.money24.client.Money24Client;
import com.ua.money24.model.ExecAsPublicRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RateSynchronizer {
    private final Money24Client money24Client;

    public RateSynchronizer(Money24Client money24Client) {
        this.money24Client = money24Client;
    }

    @Scheduled(fixedRate = 5000)
    public void myScheduledTask() {
        // Your task logic goes here
        money24Client.execAsPublic(new ExecAsPublicRequest(
                "get",
                "/rates/department-and-region-v2/2/3",
                Map.of()
        ));
    }
}