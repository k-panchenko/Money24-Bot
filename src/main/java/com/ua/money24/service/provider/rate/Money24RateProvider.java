package com.ua.money24.service.provider.rate;

import com.ua.money24.client.Money24Client;
import com.ua.money24.constants.ExecTypes;
import com.ua.money24.constants.Methods;
import com.ua.money24.exceptions.RatesNotFoundException;
import com.ua.money24.model.Rate;
import com.ua.money24.model.request.ExecAsPublicRequest;
import com.ua.money24.model.response.ExecAsPublicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component
public class Money24RateProvider implements RateProvider {
    private final Money24Client money24Client;
    private final BiFunction<ExecAsPublicResponse.Result.Rate, ExecAsPublicResponse.Result.Rate, Rate> rateBiFunction;

    public Money24RateProvider(Money24Client money24Client,
                               BiFunction<ExecAsPublicResponse.Result.Rate, ExecAsPublicResponse.Result.Rate, Rate> rateBiFunction) {
        this.money24Client = money24Client;
        this.rateBiFunction = rateBiFunction;
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
                .rates()
                .stream()
                .collect(Collectors.groupingBy(ExecAsPublicResponse.Result.Rate::id))
                .values()
                .stream()
                .flatMap(rates -> rates.stream().collect(Collectors.groupingBy(ExecAsPublicResponse.Result.Rate::type));
    }
}
