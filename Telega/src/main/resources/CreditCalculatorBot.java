package com.yourpackage;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CreditCalculatorBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "MazdaCredit_Bot"; // Имя вашего бота
    }

    @Override
    public String getBotToken() {
        return "7176542474:AAFJbodxYH-70q2zXPFCIs2SsVUZGhFVj8Y"; // Токен вашего бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String response = calculateCredit(messageText);

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(response);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String calculateCredit(String input) {
        double carPrice = 30000; // Пример значения
        double downPayment = 0.20; // Пример значения
        int loanTerm = 60; // Пример значения
        double interestRate = 6.5; // Пример значения

        double monthlyPayment = CreditCalculator.calculateMonthlyPayment(carPrice, downPayment, loanTerm, interestRate);
        return "Щомісячний платіж: " + monthlyPayment + " грн";
    }
}
