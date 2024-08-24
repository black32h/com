object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val mazda = MazdaBuilder()
                .setPrice(30000.0)
                .setDownPayment(6000.0)
                .setLoanTerm(60)
                .setInterestRate(6.5)
                .setCommission(0.0)
                .build()

            mazda.validate()
            // Додаткові розрахунки та логіка
        } catch (e: IllegalArgumentException) {
            println("Validation error: " + e.message)
        }
    }
}