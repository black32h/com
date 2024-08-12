public class LoanCalculator {
    public static void main (String[] args) {
        double carPrice = 1000000; // Вартість автомобіля в гривнях
        double downPaymentPercentage = 30; // Первинний внесок у відсотках
        double monthlyPayment = 10000; // Щомісячний платіж у гривнях

        double downPayment = carPrice * (downPaymentPercentage / 100);
        double loanAmount = carPrice - downPayment;

        double annualInterestRate = 0.1; // Приклад річної відсоткової ставки (10%)
        double monthlyInterestRate = annualInterestRate / 12;

        int months = calculateLoanTerm(loanAmount, monthlyPayment, monthlyInterestRate);

        System.out.println("Кількість місяців: " + months);
        System.out.println("Річна відсоткова ставка: " + (annualInterestRate * 100) + "%");
    }

    private static int calculateLoanTerm(double loanAmount, double monthlyPayment, double monthlyInterestRate) {
        int months = 0;
        while (loanAmount > 0) {
            loanAmount = loanAmount + (loanAmount * monthlyInterestRate) - monthlyPayment;
            months++;
        }
        return months;
    }
}
