package com.example.simpletelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class SimpleTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTelegramBotApplication.class, args);

        TelegramBot telegramBot = new TelegramBot();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession. class );
            botsApi. registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e. printStackTrace();
        }
    }

}