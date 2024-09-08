package com.example.simpletelegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    private double carPrice;
    private double downPayment;
    private double monthlyPayment;
    private int loanTerm; // Термін кредиту в місяцях
    private double[][] interestRatesPrivat = {
            {6.5, 21.9}, // 20% авансовий внесок
            {4.9, 21.9}, // 30% авансовий внесок
            {2.9, 21.9}, // 40% авансовий внесок
            {0.01, 21.9}, // 50% авансовий внесок
            {0.01, 14.9} // 60% авансовий внесок
    };
    private double[][] interestRatesOschad = {
            {6.99, 4.99, 2.99, 0.01, 0.01, 0.01}, // 12 місяців
            {10.99, 8.99, 7.99, 3.99, 0.01, 0.01}, // 24 місяці
            {11.99, 9.99, 8.99, 7.99, 4.99, 0.01}, // 25-36 місяців
            {14.99, 12.99, 12.99, 11.99, 9.99, 6.99}, // 37-60 місяців
            {15.99, 14.99, 12.99, 12.99, 10.99, 7.99} // 61-84 місяців
    };
    private double commissionRate = 3.5 / 100; // Разова комісія банку

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String answer = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            System.out.println("Отримано повідомлення: " + answer); // Логування отриманого повідомлення
            switch (answer.toLowerCase()) {
                case "start":
                    sendMessage(chat_id, "Ласкаво просимо! Будь ласка, введіть вартість автомобіля:");
                    break;
                case "price":
                    sendMessage(chat_id, "Будь ласка, введіть вартість автомобіля:");
                    break;
                case "down payment":
                    sendMessage(chat_id, "Будь ласка, введіть суму першого внеску:");
                    break;
                case "monthly payment":
                    sendMessage(chat_id, "Будь ласка, введіть місячний платіж:");
                    break;
                case "loan term":
                    sendMessage(chat_id, "Будь ласка, введіть термін кредиту в місяцях:");
                    break;
                default:
                    handleUserInput(chat_id, answer);
                    break;
            }
        }
    }

    private void handleUserInput(long chatId, String input) {
        try {
            double value = Double.parseDouble(input);
            if (carPrice == 0) {
                carPrice = value;
                sendMessage(chatId, "Вартість автомобіля встановлена на " + carPrice + ". Будь ласка, введіть суму першого внеску:");
            } else if (downPayment == 0) {
                downPayment = value;
                sendMessage(chatId, "Сума першого внеску встановлена на " + downPayment + ". Будь ласка, введіть місячний платіж:");
            } else if (monthlyPayment == 0) {
                monthlyPayment = value;
                sendMessage(chatId, "Місячний платіж встановлений на " + monthlyPayment + ". Будь ласка, введіть термін кредиту в місяцях:");
            } else if (loanTerm == 0) {
                loanTerm = (int) value;
                calculateAndSendCreditOptions(chatId);
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Невірний ввід. Будь ласка, введіть числове значення.");
        }
    }

    private void calculateAndSendCreditOptions(long chatId) {
        double loanAmount = carPrice - downPayment;
        double annualInterestRateFirstTwoYears = getAnnualInterestRateFirstTwoYears();
        double annualInterestRateAfterTwoYears = getAnnualInterestRateAfterTwoYears();
        double oneTimeCommission = loanAmount * commissionRate;
        double totalMonthlyPaymentFirstTwoYears = calculateMonthlyPayment(loanAmount, annualInterestRateFirstTwoYears, Math.min(loanTerm, 24));
        double totalMonthlyPaymentAfterTwoYears = calculateMonthlyPayment(loanAmount, annualInterestRateAfterTwoYears, Math.max(0, loanTerm - 24));

        StringBuilder message = new StringBuilder();
        message.append("Сума кредиту: ").append(loanAmount).append("\n");
        message.append("Річна процентна ставка (перші два роки): ").append(annualInterestRateFirstTwoYears).append("%\n");
        message.append("Річна процентна ставка (після двох років): ").append(annualInterestRateAfterTwoYears).append("%\n");
        message.append("Разова комісія: ").append(oneTimeCommission).append("\n");
        message.append("Загальний місячний платіж (перші два роки): ").append(totalMonthlyPaymentFirstTwoYears).append("\n");
        message.append("Загальний місячний платіж (після двох років): ").append(totalMonthlyPaymentAfterTwoYears).append("\n");

        // Додати логіку для визначення найбільш підходящого банку
        String bestBank = determineBestBank();
        message.append("Найбільш підходящий банк для вас: ").append(bestBank).append("\n");

        sendMessage(chatId, message.toString());
    }

    private double getAnnualInterestRateFirstTwoYears() {
        // Реалізуйте логіку для визначення відповідної річної процентної ставки на перші два роки
        // Для простоти, повертаємо фіксовану ставку тут
        return 6.5;
    }

    private double getAnnualInterestRateAfterTwoYears() {
        // Реалізуйте логіку для визначення відповідної річної процентної ставки після двох років
        // Для простоти, повертаємо фіксовану ставку тут
        return 21.9;
    }

    private double calculateMonthlyPayment(double loanAmount, double annualInterestRate, int loanTerm) {
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        return (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanTerm));
    }

    private String determineBestBank() {
        // Реалізуйте логіку для визначення найбільш підходящого банку
        // Для простоти, повертаємо фіксоване значення тут
        return "ПриватБанк";
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        System.out.println("Відправка повідомлення: " + text); // Логування відправленого повідомлення
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "MazdaCredit_Bot"; // Вкажіть ім'я вашого бота
    }

    @Override
    public String getBotToken() {
        return "7176542474:AAFJbodxYH-70q2zXPFCIs2SsVUZGhFVj8Y"; // Вкажіть токен вашого бота
    }
}
