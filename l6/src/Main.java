import java.util.ArrayList;
import java.util.List;

/**
 * Виконавчий клас для Лабораторної роботи №6.
 * Демонструє роботу власної колекції MySweetList
 * з використанням ієрархії солодощів з ЛР5.
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Створюємо кілька солодощів, як у ЛР5
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

            System.out.println("=== Демонстрація роботи MySweetList ===");

            // 1) Порожній конструктор
            MySweetList<Sweet> list1 = new MySweetList<>();
            list1.add(candy1);
            list1.add(candy2);
            list1.add(candy3);

            System.out.println("\nСписок list1 після додавання трьох елементів:");
            printList(list1);

            // 2) Конструктор з одним елементом
            MySweetList<Sweet> list2 = new MySweetList<>(candy4);
            System.out.println("\nСписок list2 (створений з одним елементом):");
            printList(list2);

            // 3) Конструктор зі стандартної колекції
            List<Sweet> temp = new ArrayList<>();
            temp.add(candy4);
            temp.add(candy5);
            MySweetList<Sweet> list3 = new MySweetList<>(temp);

            System.out.println("\nСписок list3 (створений зі стандартної колекції):");
            printList(list3);

            // Додаємо всі елементи list3 в list1 на позицію 1
            list1.addAll(1, list3);
            System.out.println("\nСписок list1 після addAll(1, list3):");
            printList(list1);

            // Перевірка contains та indexOf
            System.out.println("\nЧи містить list1 'Ромашка'? " + list1.contains(candy2));
            System.out.println("Індекс 'Ромашка' у list1: " + list1.indexOf(candy2));

            // Видалення по індексу
            System.out.println("\nВидаляємо елемент на індексі 2:");
            Sweet removed = list1.remove(2);
            System.out.println("Видалено: " + removed);
            System.out.println("Список list1 після видалення:");
            printList(list1);

            // Видалення по об'єкту
            System.out.println("\nВидаляємо 'Чупа-чупс' за об'єктом:");
            boolean removedByObject = list1.remove(candy5);
            System.out.println("Успішно видалено? " + removedByObject);
            printList(list1);

            // Тестувємо get та set
            System.out.println("\nЕлемент на індексі 0: " + list1.get(0));
            System.out.println("Замінюємо елемент на індексі 0 на 'Milka'");
            list1.set(0, candy4);
            printList(list1);

            // Перевіримо обробку помилки індексу
            try {
                System.out.println("\nСпроба отримати елемент з індексом 100:");
                list1.get(100);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Очікувана помилка: " + e.getMessage());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка ініціалізації даних: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Несподівана помилка: " + e.getMessage());
        }
    }

    /**
     * Допоміжний метод для виведення вмісту списку.
     */
    private static void printList(List<Sweet> list) {
        System.out.println("Розмір: " + list.size());
        int index = 0;
        for (Sweet sweet : list) {
            System.out.println("  [" + index + "] " + sweet);
            index++;
        }
    }
}
