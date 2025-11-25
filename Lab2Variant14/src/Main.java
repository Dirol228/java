import java.util.Scanner;

/**
 * Лабораторна робота №2
 * Тема: Робота з рядками у Java
 * Варіант: 14 (C3 = 2 → StringBuffer, C17 = 14 → видалити повторні входження першої літери)
 *
 * Важливо:
 *  - Для роботи з рядками дозволено використовувати тільки StringBuffer.
 *  - Перетворення між String та StringBuffer дозволяється лише під час введення та виведення даних.
 *  - Розбиття на слова та всі операції повинні виконуватись вручну, посимвольно.
 */

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введіть текст:");
            String input = scanner.nextLine(); // допустиме використання String тільки для вводу

            StringBuffer text = new StringBuffer(input); // робочий буфер

            StringBuffer result = new StringBuffer();

            int i = 0;
            int length = text.length();

            // Посимвольне розбиття тексту на слова
            while (i < length) {

                // Пропуск пробілів
                while (i < length && text.charAt(i) == ' ') {
                    result.append(' ');
                    i++;
                }

                // Збір слова у StringBuffer
                StringBuffer word = new StringBuffer();
                while (i < length && text.charAt(i) != ' ') {
                    word.append(text.charAt(i));
                    i++;
                }

                // Обробка слова згідно варіанту
                if (word.length() > 0) {
                    result.append(processWord(word));
                }
            }

            // Виведення результату (дозволено конвертувати у String)
            System.out.println("\n===== Результат обробки =====");
            System.out.println(result.toString());

        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    /**
     * Обробляє слово: видаляє всі повторні входження першої літери.
     * Використовується виключно StringBuffer.
     */
    public static StringBuffer processWord(StringBuffer word) {
        if (word.length() == 0) {
            return word;
        }

        StringBuffer processed = new StringBuffer();

        char first = word.charAt(0);
        processed.append(first);

        // Додаємо символи, які не повторюють першу літеру
        for (int i = 1; i < word.length(); i++) {
            char current = word.charAt(i);

            // Порівняння у нижньому регістрі для коректності
            if (Character.toLowerCase(current) != Character.toLowerCase(first)) {
                processed.append(current);
            }
        }

        return processed;
    }
}
