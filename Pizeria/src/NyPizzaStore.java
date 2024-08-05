public class NyPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NyPizzaIngredientFactory();

        if (item.equals("cheese")) {
            pizza = new CheesePizza(ingredientFactory);
            pizza.name = "New York Style Cheese Pizza";
        } else if (item.equals("veggie")) {
            pizza = new VeggiePizza(ingredientFactory);
            pizza.name = "New York Style Veggie Pizza";
        } else if (item.equals("clam")) {
            pizza = new ClamPizza(ingredientFactory);
            pizza.name = "New York Style Clam Pizza";
        } else if (item.equals("pepperoni")) {
            pizza = new PepperoniPizza(ingredientFactory);
            pizza.name = "New York Style Pepperoni Pizza";
        }

        return pizza;
    }
}
