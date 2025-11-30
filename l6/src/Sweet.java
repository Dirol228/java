/**
 * Абстрактний клас Sweet представляє солодощі.
 */
public abstract class Sweet {

    private final String name;
    private final double weight;
    private final double price;
    private final double chocolatePercent;

    public Sweet(String name, double weight, double price, double chocolatePercent) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва не може бути порожньою.");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Вага повинна бути > 0.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від’ємною.");
        }
        if (chocolatePercent < 0 || chocolatePercent > 100) {
            throw new IllegalArgumentException("Вміст шоколаду повинен бути 0–100%.");
        }

        this.name = name;
        this.weight = weight;
        this.price = price;
        this.chocolatePercent = chocolatePercent;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public double getChocolatePercent() {
        return chocolatePercent;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " '" + name + '\'' +
                ", вага=" + weight + " г" +
                ", ціна=" + price + " грн" +
                ", шоколад=" + chocolatePercent + "%";
    }
}
