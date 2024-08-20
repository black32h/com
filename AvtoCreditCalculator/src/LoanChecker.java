public class LoanChecker {

    public void checkLoanOptions(double loanAmount, double downPayment, double[] rates, int[] terms, double maxMonthlyPayment) {///Цей метод перевіряє, чи може клієнт отримати кредит із заданими умовами, і якщо ні, пропонує максимально наближену до його умов пропозицію
        boolean foundSuitableLoan = false;

        for (int i = 0; i < rates.length; i++) {
            double adjustedLoanAmount = loanAmount;
            if (terms[i] != 48 && terms[i] != 60) {
                adjustedLoanAmount += loanAmount * 0.0299;
            }
            double monthlyPayment = CarLoanCalculator.calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]);
            if (monthlyPayment <= maxMonthlyPayment) {
                System.out.printf("Клієнт може дозволити собі кредит на %d місяців під %.2f%% річних, з щомісячним платежем of %.2f\n", terms[i], rates[i], monthlyPayment);
                if (terms[i] != 48 && terms[i] != 60) {
                    System.out.println("Це включає одноразову комісію.");
                }
                System.out.printf("Початковий внесок: %.2f\n", downPayment);
                foundSuitableLoan = true;
                break;
            }
        }

        if (!foundSuitableLoan) {
            double closestMonthlyPayment = Double.MAX_VALUE;
            int closestTerm = 0;
            double closestRate = 0;

            for (int i = 0; i < rates.length; i++) {
                double adjustedLoanAmount = loanAmount;
                if (terms[i] != 48 && terms[i] != 60) {
                    adjustedLoanAmount += loanAmount * 0.0299;
                }
                double monthlyPayment = CarLoanCalculator.calculateMonthlyPayment(adjustedLoanAmount, rates[i], terms[i]);
                if (Math.abs(monthlyPayment - maxMonthlyPayment) < Math.abs(closestMonthlyPayment - maxMonthlyPayment)) {
                    closestMonthlyPayment = monthlyPayment;
                    closestTerm = terms[i];
                    closestRate = rates[i];
                }
            }

            System.out.printf("Найближчий варіант кредиту на %d місяців під %.2f%% річних, з щомісячною платежем в розмірі %.2f\n ( Без одноразової коміссії )", closestTerm, closestRate, closestMonthlyPayment);
            if (closestTerm != 48 && closestTerm != 60) {
                System.out.println("Це включає одноразову комісію.");
            }
            System.out.printf("Початковий внесок: %.2f\n", downPayment);
        }
    }
}
