/**
 * Виконавчий клас для демонстрації наслідування та поліморфізму
 * на прикладі ієрархії цукерок та дитячого подарунка.
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Створюємо кілька різних солодощів (поліморфізм)
            Sweet candy1 = new ChocolateCandy("Корона з горіхом",
                    50, 25.0, 70.0, "темне");
            Sweet candy2 = new CaramelCandy("Ромашка",
                    30, 10.0, 10.0, "вершкова");
            Sweet candy3 = new JellyCandy("Мармеладки фруктові",
                    40, 15.0, 0.0, "полуниця");
            Sweet candy4 = new ChocolateCandy("Milka",
                    45, 30.0, 80.0, "молочне");
            Sweet candy5 = new CaramelCandy("Чупа-чупс",
                    20, 8.0, 5.0, "фруктова");

            // Формуємо масив
            Sweet[] sweets = {candy1, candy2, candy3, candy4, candy5};

            // Створюємо подарунок
            Gift gift = new Gift(sweets);

            // Виводимо вміст
            System.out.println("=== Початковий подарунок ===");
            gift.printGiftContents();

            // Розрахунок загальної ваги та вартості
            double totalWeight = gift.calculateTotalWeight();
            double totalPrice = gift.calculateTotalPrice();
            System.out.println("\nЗагальна вага подарунка: " + totalWeight + " г");
            System.out.println("Загальна вартість подарунка: " + totalPrice + " грн");

            // Сортування за вагою
            gift.sortByWeight();
            System.out.println("\n=== Подарунок після сортування за вагою ===");
            gift.printGiftContents();

            // Пошук цукерок за діапазоном вмісту шоколаду
            double minChocolate = 10.0;
            double maxChocolate = 80.0;
            System.out.println("\nПошук цукерок з вмістом шоколаду у діапазоні ["
                    + minChocolate + "; " + maxChocolate + "]%");

            Sweet[] found = gift.findByChocolatePercentRange(minChocolate, maxChocolate);

            if (found.length == 0) {
                System.out.println("Цукерок з таким вмістом шоколаду не знайдено.");
            } else {
                System.out.println("Знайдені цукерки:");
                for (Sweet sweet : found) {
                    System.out.println("  - " + sweet);
                }
            }

        } catch (InvalidChocolateRangeException e) {
            System.out.println("Помилка діапазону: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка ініціалізації даних: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Несподівана помилка: " + e.getMessage());
        }
    }
}
