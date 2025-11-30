/**
 * Абстрактний клас Sweet представляє солодощі.
 * Містить базові властивості, спільні для всіх цукерок.
 */
public abstract class Sweet {

    private final String name;                // назва
    private final double weight;              // вага в грамах
    private final double price;               // ціна за одиницю (грн)
    private final double chocolatePercent;    // вміст шоколаду в %, 0–100

    /**
     * Конструктор солодощів.
     *
     * @param name             назва солодощів
     * @param weight           вага в грамах (повинна бути > 0)
     * @param price            ціна за одиницю в грн (повинна бути >= 0)
     * @param chocolatePercent відсоток вмісту шоколаду (0–100)
     * @throws IllegalArgumentException якщо параметри некоректні
     */
    public Sweet(String name, double weight, double price, double chocolatePercent) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва не може бути порожньою.");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Вага повинна бути більшою за 0.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від’ємною.");
        }
        if (chocolatePercent < 0 || chocolatePercent > 100) {
            throw new IllegalArgumentException("Вміст шоколаду повинен бути в діапазоні 0–100%.");
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

    /**
     * Повертає текстовий опис типу солодощів (для поліморфного відображення).
     */
    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " '" + name + '\'' +
                ", вага=" + weight + " г" +
                ", ціна=" + price + " грн" +
                ", шоколад=" + chocolatePercent + "%";
    }
}
