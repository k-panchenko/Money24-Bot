package com.ua.money24.bot;

import com.ua.money24.config.TelegramBotProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Money24Bot extends TelegramLongPollingCommandBot {
    private final String botUsername;

    public Money24Bot(TelegramBotProperties telegramBotProperties) {
        super(telegramBotProperties.token());
        this.botUsername = telegramBotProperties.username();
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }
}
