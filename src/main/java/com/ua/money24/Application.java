package com.ua.money24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@Import(TelegramBotStarterConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}