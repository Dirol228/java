import java.util.Arrays;
import java.util.Comparator;

/**
 * Головний клас програми.
 * Створює масив морських човнів, сортує його та виконує пошук заданого об'єкта.
 */
public class Main {

    public static void main(String[] args) {

        // --- Створення масиву об'єктів ---
        SeaBoat[] boats = {
                new SeaBoat("Dolphin", "Катер", 12.5, 3.2, 8),
                new SeaBoat("OceanStar", "Яхта", 25.0, 12.0, 20),
                new SeaBoat("SeaFox", "Шлюпка", 5.0, 0.7, 4),
                new SeaBoat("WindRider", "Катер", 10.0, 2.1, 6),
                new SeaBoat("AquaSpirit", "Яхта", 30.0, 15.0, 25)
        };

        System.out.println("=== Початковий масив ===");
        for (SeaBoat boat : boats) {
            System.out.println(boat);
        }

        // --- Сортування ---
        // Сортувати за довжиною — за зростанням
        Arrays.sort(boats, Comparator.comparingDouble(SeaBoat::getLength));

        // Потім за вмістимістю — за спаданням
        Arrays.sort(boats, Comparator.comparingInt(SeaBoat::getCapacity).reversed());

        System.out.println("\n=== Відсортований масив ===");
        for (SeaBoat boat : boats) {
            System.out.println(boat);
        }

        // --- Пошук ідентичного об'єкта ---
        SeaBoat target = new SeaBoat("SeaFox", "Шлюпка", 5.0, 0.7, 4);

        System.out.println("\nПошук об'єкта: " + target);

        int foundIndex = -1;
        for (int i = 0; i < boats.length; i++) {
            if (boats[i].equals(target)) {
                foundIndex = i;
                break;
            }
        }

        if (foundIndex != -1) {
            System.out.println("Об'єкт знайдено на позиції: " + foundIndex);
            System.out.println("Знайдений об'єкт: " + boats[foundIndex]);
        } else {
            System.out.println("Об'єкт не знайдено.");
        }
    }
}
