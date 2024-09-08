package com.example.simpletelegrambot;public class CarLoanCalculator {

    public static void main(String[] args) {////Параметри кредиту
        double carPrice = 1000000;
        double downPayment = carPrice * 0.30;
        double loanAmount = carPrice - downPayment;

        double[] rates = {3.49, 6.99, 8.99, 11.99, 11.99};///масив відцоткової ставки
        int[] terms = {12, 24, 36, 48, 60}; ///масив терміну кредиту

        for (int i = 0; i < rates.length; i++) {////виклик методів для розрахунку і перевірки кредитних умов
            double adjustedLoanAmount = loanAmount;
            if (terms[i] != 48 && terms[i] != 60) {
                adjustedLoanAmount += loanAmount * 0.0299;
                System.out.printf("Кредит на %d місяців під %.2f%% річних (включае одноразову коміссію): платіж %.2f\n", terms[i], rates[i], calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]));
                System.out.println("Одноразова коміссія складае 2,99% від сумми кредиту");
            } else {
                System.out.printf("Кредит на %d місяців під %.2f%% річних (без одноразовой коміссії): платіж %.2f\n", terms[i], rates[i], calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]));
            }
        }

        LoanChecker loanChecker = new LoanChecker();
        loanChecker.checkLoanOptions(loanAmount, downPayment, rates, terms, 10000);
    }

    public static double calculateMonthlyPayment(double loanAmount, double annualRate, int months) {//цей метод використовується для розрахунку щомісячного платіжу на основі сумми, річної ставки і терміну кредита
        double monthlyRate = annualRate / 100 / 12;
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }
}