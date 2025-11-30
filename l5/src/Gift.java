import java.util.Arrays;
import java.util.Comparator;

/**
 * Клас Gift представляє дитячий подарунок,
 * що складається з масиву солодощів.
 */
public class Gift {

    private final Sweet[] sweets;

    /**
     * Створює подарунок з масиву солодощів.
     *
     * @param sweets масив солодощів (не повинен бути null або порожнім)
     */
    public Gift(Sweet[] sweets) {
        if (sweets == null || sweets.length == 0) {
            throw new IllegalArgumentException("Подарунок не може бути порожнім.");
        }
        this.sweets = sweets;
    }

    public Sweet[] getSweets() {
        return sweets;
    }

    /**
     * Обчислює загальну вагу подарунка.
     *
     * @return сума ваги всіх солодощів у грамах
     */
    public double calculateTotalWeight() {
        double total = 0.0;
        for (Sweet sweet : sweets) {
            total += sweet.getWeight();
        }
        return total;
    }

    /**
     * Обчислює загальну вартість подарунка.
     *
     * @return сума цін всіх солодощів
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Sweet sweet : sweets) {
            total += sweet.getPrice();
        }
        return total;
    }

    /**
     * Сортує солодощі у подарунку за вагою за зростанням.
     */
    public void sortByWeight() {
        Arrays.sort(sweets, Comparator.comparingDouble(Sweet::getWeight));
    }

    /**
     * Знаходить усі солодощі, вміст шоколаду яких
     * знаходиться в заданому діапазоні [min; max].
     *
     * @param min мінімальний відсоток шоколаду
     * @param max максимальний відсоток шоколаду
     * @return масив знайдених солодощів (може бути порожнім)
     * @throws InvalidChocolateRangeException якщо діапазон некоректний
     */
    public Sweet[] findByChocolatePercentRange(double min, double max)
            throws InvalidChocolateRangeException {
        if (min < 0 || max > 100 || min > max) {
            throw new InvalidChocolateRangeException(
                    "Некоректний діапазон вмісту шоколаду: [" + min + "; " + max + "]");
        }

        return Arrays.stream(sweets)
                .filter(sweet -> sweet.getChocolatePercent() >= min
                        && sweet.getChocolatePercent() <= max)
                .toArray(Sweet[]::new);
    }

    /**
     * Виводить інформацію про всі цукерки в подарунку.
     */
    public void printGiftContents() {
        System.out.println("Вміст подарунка:");
        for (Sweet sweet : sweets) {
            System.out.println("  - " + sweet);
        }
    }
}
