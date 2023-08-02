package com.ua.money24.provider.rate;

import com.ua.money24.client.Money24Client;
import com.ua.money24.constants.ExecTypes;
import com.ua.money24.constants.Methods;
import com.ua.money24.exceptions.RatesNotFoundException;
import com.ua.money24.model.Rate;
import com.ua.money24.model.request.ExecAsPublicRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Money24RateProvider implements RateProvider {
    private final Money24Client money24Client;

    public Money24RateProvider(Money24Client money24Client) {
        this.money24Client = money24Client;
    }

    @Override
    public List<Rate> getRatesInRegion(int regionId) {
        var response = money24Client.execAsPublic(new ExecAsPublicRequest(
                ExecTypes.GET,
                String.format(Methods.RATES, regionId),
                null
        ));
        if (response.statusCode() != HttpStatus.OK.value())
            throw new RatesNotFoundException();
        return response.result()
                .rates();
    }
}
