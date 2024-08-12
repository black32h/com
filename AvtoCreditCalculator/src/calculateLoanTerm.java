public class calculateLoanTerm{
private static int calculateLoanTerm(double loanAmount, double monthlyPayment, double monthlyInterestRate) {
    int months = 0;
    while (loanAmount > 0) {
        loanAmount = loanAmount + (loanAmount * monthlyInterestRate) - monthlyPayment;
        months++;
    }
    return months;
}

}
