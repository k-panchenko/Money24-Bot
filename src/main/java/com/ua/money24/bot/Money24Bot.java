package com.ua.money24.bot;

import com.ua.money24.config.TelegramBotProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            var command = getRegisteredCommand(update.getMessage().getText());
            if (Objects.isNull(command)) {
                return;
            }
            command.processMessage(this, update.getMessage(), new String[]{});
        }

    }
}
