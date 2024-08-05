import java.util.ArrayList;

public abstract class Pizza {
    String name;
    Dough dough;
    Sauce sauce;
    Veggies[] veggies;
    Cheese cheese;
    Pepperoni pepperoni;
    Clams clams;
    ArrayList<String> toppings = new ArrayList<>();

    abstract void prepare();

    void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }

    void cut() {
        System.out.println("Cutting the pizza");
    }

    void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append("---- ").append(name).append(" ----\n");
        if (dough != null) {
            display.append(dough).append("\n");
        }
        if (sauce != null) {
            display.append(sauce).append("\n");
        }
        if (veggies != null) {
            for (Veggies veggie : veggies) {
                display.append(veggie).append("\n");
            }
        }
        if (cheese != null) {
            display.append(cheese).append("\n");
        }
        if (pepperoni != null) {
            display.append(pepperoni).append("\n");
        }
        if (clams != null) {
            display.append(clams).append("\n");
        }
        return display.toString();
    }
}
