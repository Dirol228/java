/**
 * Клас SeaBoat представляє морський човен.
 * Містить базові характеристики човна: назва, тип, довжина, вага та вмістимість.
 *
 * @author …
 */
public class SeaBoat {

    private String name;     // Назва човна
    private String type;     // Тип (катер, яхта тощо)
    private double length;   // Довжина у метрах
    private double weight;   // Вага у тоннах
    private int capacity;    // Максимальна кількість людей

    /**
     * Конструктор морського човна.
     */
    public SeaBoat(String name, String type, double length, double weight, int capacity) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.weight = weight;
        this.capacity = capacity;
    }

    // --- ГЕТТЕРИ ---

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getLength() {
        return length;
    }

    public double getWeight() {
        return weight;
    }

    public int getCapacity() {
        return capacity;
    }

    // --- ПЕРЕВИЗНАЧЕННЯ equals() ТА hashCode() ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SeaBoat boat = (SeaBoat) obj;

        return Double.compare(boat.length, length) == 0
                && Double.compare(boat.weight, weight) == 0
                && capacity == boat.capacity
                && name.equals(boat.name)
                && type.equals(boat.type);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + Double.hashCode(length);
        result = 31 * result + Double.hashCode(weight);
        result = 31 * result + capacity;
        return result;
    }

    @Override
    public String toString() {
        return "SeaBoat{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", length=" + length +
                ", weight=" + weight +
                ", capacity=" + capacity +
                '}';
    }
}
