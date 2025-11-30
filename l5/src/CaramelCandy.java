/**
 * Клас CaramelCandy описує карамельні цукерки.
 */
public class CaramelCandy extends Sweet {

    private final String filling; // начинка, наприклад "фруктова", "вершкова"

    public CaramelCandy(String name,
                        double weight,
                        double price,
                        double chocolatePercent,
                        String filling) {
        super(name, weight, price, chocolatePercent);

        if (filling == null || filling.isBlank()) {
            throw new IllegalArgumentException("Начинка не може бути порожньою.");
        }
        this.filling = filling;
    }

    public String getFilling() {
        return filling;
    }

    @Override
    public String getType() {
        return "Карамельна цукерка";
    }

    @Override
    public String toString() {
        return super.toString() + ", начинка='" + filling + '\'';
    }
}
