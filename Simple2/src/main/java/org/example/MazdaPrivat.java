package com.example.simpletelegrambot;public class MazdaPrivat {
    public static void main(String[] args) {
        double carPrice = 3000000; // Ціна автомобіля
        double[] downPayments = {0.20, 0.30, 0.40, 0.50, 0.60}; // Відсотки авансового внеску
        int loanTerm = 60; // Строк кредитування в місяцях
        double[][] interestRates = {
                {6.5, 21.9}, // 20% авансовий внесок
                {4.9, 21.9}, // 30% авансовий внесок
                {2.9, 21.9}, // 40% авансовий внесок
                {0.01, 21.9}, // 50% авансовий внесок
                {0.01, 14.9} // 60% авансовий внесок
        };

        for (int i = 0; i < downPayments.length; i++) {
            double downPayment = carPrice * downPayments[i];
            double loanAmount = carPrice - downPayment;
            double interestRateFirstTwoYears = interestRates[i][0] / 100;
            double interestRateAfterTwoYears = interestRates[i][1] / 100;

            // Розрахунок щомісячного платежу для перших двох років
            double monthlyRateFirstTwoYears = interestRateFirstTwoYears / 12;
            double monthlyPaymentFirstTwoYears = (loanAmount * monthlyRateFirstTwoYears) / (1 - Math.pow(1 + monthlyRateFirstTwoYears, -24));

            // Розрахунок щомісячного платежу для решти терміну
            double remainingLoanAmount = loanAmount - (monthlyPaymentFirstTwoYears * 24);
            double monthlyRateAfterTwoYears = interestRateAfterTwoYears / 12;
            double monthlyPaymentAfterTwoYears = (remainingLoanAmount * monthlyRateAfterTwoYears) / (1 - Math.pow(1 + monthlyRateAfterTwoYears, -(loanTerm - 24)));

            // Загальна сума виплат
            double totalPayment = (monthlyPaymentFirstTwoYears * 24) + (monthlyPaymentAfterTwoYears * (loanTerm - 24));

            System.out.printf("Авансовий внесок: %.0f%%, Щомісячний платіж перші 2 роки: %.2f грн, Щомісячний платіж після 2 років: %.2f грн, Загальна сума виплат: %.2f грн\n",
                    downPayments[i] * 100, monthlyPaymentFirstTwoYears, monthlyPaymentAfterTwoYears, totalPayment);
        }
    }
}
