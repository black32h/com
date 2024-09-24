package org.example;public class MazdaBuilder {
    private double price;
    private double downPayment;
    private int loanTerm;
    private double interestRate;
    private double commission;

    public MazdaBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public MazdaBuilder setDownPayment(double downPayment) {
        this.downPayment = downPayment;
        return this;
    }

    public MazdaBuilder setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
        return this;
    }

    public MazdaBuilder setInterestRate(double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public MazdaBuilder setCommission(double commission) {
        this.commission = commission;
        return this;
    }

    public com.example.simpletelegrambot.Mazda build() {
        return new com.example.simpletelegrambot.Mazda(price, downPayment, loanTerm, interestRate, commission);
    }
}
