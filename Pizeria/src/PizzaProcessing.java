public class PizzaProcessing {
    Pizza createPizza() {
        Pizza pizza = new Pizza() {
            @Override
            public void prepare() {
                System.out.println("Preparing " + name);
            }

            @Override
            public void bake() {
                System.out.println("Bake for 25 minutes at 350");
            }

            @Override
            public void cut() {
                System.out.println("Cutting the pizza");
            }

            @Override
            public void box() {
                System.out.println("Place pizza in official PizzaStore box");
            }
        };

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
