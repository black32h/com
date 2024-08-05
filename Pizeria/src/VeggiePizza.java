public class VeggiePizza extends Pizza {
    PizzaIngredientFactory ingredientFactory;

    public VeggiePizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    void prepare() {
        System.out.println("Preparing " + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        veggies = ingredientFactory.createVeggies();
    }

    @Override
    void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }

    @Override
    void cut() {
        System.out.println("Cutting the pizza");
    }

    @Override
    void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }
}
