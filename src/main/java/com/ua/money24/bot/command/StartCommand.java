package com.ua.money24.bot.command;

import com.ua.money24.constants.Messages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartCommand extends BotCommand {
    private final String money24Url;

    public StartCommand(@Value("start") String command,
                        @Value("Starts the bot") String description,
                        @Value("${spring.cloud.openfeign.client.config.money24Client.url}") String money24Url) {
        super(command, description);
        this.money24Url = money24Url;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        var sendMessage = new SendMessage(chat.getId().toString(), String.format(Messages.START, money24Url));
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
