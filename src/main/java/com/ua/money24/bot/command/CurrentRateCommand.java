package com.ua.money24.bot.command;

import com.ua.money24.constants.Messages;
import com.ua.money24.helper.CurrencyCallbackData;
import com.ua.money24.helper.InlineKeyboardMarkupWrapper;
import com.ua.money24.model.Rate;
import com.ua.money24.model.Subscriber;
import com.ua.money24.service.provider.currency.CurrencyProvider;
import com.ua.money24.service.provider.rate.RateProvider;
import com.ua.money24.service.provider.subscriber.SubscriberProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.function.Function;

@Component
public class CurrentRateCommand implements IBotCommand {
    private final RateProvider rateProvider;
    private final CurrencyProvider currencyProvider;
    private final SubscriberProvider subscriberProvider;
    private final Function<Rate, String> rateStringFunction;
    private final String commandIdentifier;
    private final String description;
    private final Integer defaultCurrencyId;

    public CurrentRateCommand(RateProvider internalRateProvider,
                              CurrencyProvider currencyProvider,
                              SubscriberProvider subscriberProvider,
                              Function<Rate, String> rateStringFunction,
                              @Value(Messages.CURRENT_RATE) String commandIdentifier,
                              @Value("Returns current exchange rates") String description,
                              @Value("${application.default-currency-id:0}") Integer defaultCurrencyId) {
        this.rateProvider = internalRateProvider;
        this.currencyProvider = currencyProvider;
        this.subscriberProvider = subscriberProvider;
        this.rateStringFunction = rateStringFunction;
        this.commandIdentifier = commandIdentifier;
        this.description = description;
        this.defaultCurrencyId = defaultCurrencyId;
    }

    @Override
    public String getCommandIdentifier() {
        return commandIdentifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        var currencyId = arguments.length > 0 ? Integer.parseInt(arguments[0]) : defaultCurrencyId;
        var selectedCurrency = currencyProvider.getCurrencyById(currencyId);
        var currencies = currencyProvider.getAll();
        var subscriber = subscriberProvider.findById(message.getChatId())
                .orElseGet(() -> subscriberProvider.save(new Subscriber(message.getChatId(), null)));
        var rate = rateProvider.getRateByRegionAndCurrency(subscriber.regionId(), selectedCurrency.id());
        var sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(rateStringFunction.apply(rate))
                .replyMarkup(new InlineKeyboardMarkupWrapper(4).add(currencies.stream()
                        .map(currency -> InlineKeyboardButton.builder()
                                .text(currency.flag())
                                .callbackData(new CurrencyCallbackData(currency.id().toString()).toString())
                                .build())
                        .toArray(InlineKeyboardButton[]::new)))
                .build();
        absSender.execute(sendMessage);
    }
}
