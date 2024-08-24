public class OchadMazda {
    public static void main(String[] args) {
        double carPrice = 3000000; // Ціна автомобіля
        double[] downPayments = {0.20, 0.30, 0.40, 0.50, 0.60, 0.70}; // Відсотки авансового внеску
        int[] loanTerms = {12, 24, 36, 60, 84}; // Строки кредитування в місяцях
        double[][] interestRates = {
                {6.99, 4.99, 2.99, 0.01, 0.01, 0.01}, // 12 місяців
                {10.99, 8.99, 7.99, 3.99, 0.01, 0.01}, // 24 місяці
                {11.99, 9.99, 8.99, 7.99, 4.99, 0.01}, // 25-36 місяців
                {14.99, 12.99, 12.99, 11.99, 9.99, 6.99}, // 37-60 місяців
                {15.99, 14.99, 12.99, 12.99, 10.99, 7.99} // 61-84 місяців
        };
        double commissionRate = 3.5 / 100; // Разова комісія банку

        for (int termIndex = 0; termIndex < loanTerms.length; termIndex++) {
            int loanTerm = loanTerms[termIndex];
            System.out.printf("Термін кредитування: %d місяців\n", loanTerm);
            for (int downPaymentIndex = 0; downPaymentIndex < downPayments.length; downPaymentIndex++) {
                double downPayment = carPrice * downPayments[downPaymentIndex];
                double loanAmount = carPrice - downPayment;
                double interestRate = interestRates[termIndex][downPaymentIndex] / 100;
                double monthlyRate = interestRate / 12;
                double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loanTerm));
                double commission = loanAmount * commissionRate;
                double totalPayment = monthlyPayment * loanTerm + commission;

                System.out.printf("Авансовий внесок: %.0f%%, Щомісячний платіж: %.2f грн, Загальна сума виплат: %.2f грн, Разова комісія: %.2f грн\n",
                        downPayments[downPaymentIndex] * 100, monthlyPayment, totalPayment, commission);
            }
            System.out.println();
        }
    }
}
