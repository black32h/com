public class PepperoniPizza extends Pizza {
    PizzaIngredientFactory ingredientFactory;

    public PepperoniPizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    void prepare() {
        System.out.println("Preparing " + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        pepperoni = ingredientFactory.createPepperoni();
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
