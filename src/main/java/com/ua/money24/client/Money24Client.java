package com.ua.money24.client;

import com.ua.money24.model.request.ExecAsPublicRequest;
import com.ua.money24.model.response.ExecAsPublicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "money24Client",
        path = "/00000000-0000-0000-0000-000000000000/api",
        url = "${spring.cloud.openfeign.client.config.money24Client.url}" // Path without url doesn't work :(
)
public interface Money24Client {
    @PostMapping("/exec-as-public")
    ExecAsPublicResponse execAsPublic(@RequestBody ExecAsPublicRequest request);
}
