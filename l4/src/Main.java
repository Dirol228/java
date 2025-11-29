import java.util.ArrayList;
import java.util.Scanner;

/**
 * Лабораторна робота №4
 * Композиція класів. Робота з текстом.
 *
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть текст:");
        String input = scanner.nextLine();

        // Нормалізація пробілів
        input = input.replaceAll("[ \\t]+", " ");

        ArrayList<Sentence> sentences = new ArrayList<>();

        StringBuilder buffer = new StringBuilder();
        char lastPunctuation = 0;
        boolean hasPunctuation = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);

            if (PunctuationMark.isPunctuation(c)) {
                // Запам'ятовуємо перший розділовий знак
                if (!hasPunctuation) {
                    lastPunctuation = c;
                    hasPunctuation = true;
                }
            } else {
                // Якщо був блок пунктуації — завершуємо речення
                if (hasPunctuation) {
                    sentences.add(parseSentence(buffer.toString(), lastPunctuation));
                    buffer.setLength(0);
                    hasPunctuation = false;
                }
            }
        }

        // Якщо кінець тексту без пунктуації — теж речення
        if (buffer.length() > 0) {
            sentences.add(parseSentence(buffer.toString(), hasPunctuation ? lastPunctuation : 0));
        }

        // Формуємо текст
        Text text = new Text(sentences.toArray(new Sentence[0]));

        System.out.println("\n===== Результат обробки =====");
        System.out.println(text);
    }

    /**
     * Перетворює рядок на Sentence-об'єкт.
     * @param s — текст речення
     * @param punctuationChar — символ пунктуації або 0, якщо нема
     */
    private static Sentence parseSentence(String s, char punctuationChar) {
        s = s.trim();

        PunctuationMark punctuation = null;
        if (punctuationChar != 0) {
            punctuation = new PunctuationMark(punctuationChar);
            // видаляємо пунктуацію з кінця рядка
            while (s.length() > 0 && PunctuationMark.isPunctuation(s.charAt(s.length() - 1))) {
                s = s.substring(0, s.length() - 1);
            }
        }

        if (s.isEmpty()) {
            return new Sentence(new Word[0], punctuation);
        }

        String[] splitWords = s.split(" ");
        Word[] words = new Word[splitWords.length];

        for (int i = 0; i < splitWords.length; i++) {
            String w = splitWords[i];

            Letter[] letters = new Letter[w.length()];
            for (int j = 0; j < w.length(); j++) {
                letters[j] = new Letter(w.charAt(j));
            }

            Word word = new Word(letters);
            word.process(); // алгоритм ЛР2
            words[i] = word;
        }

        return new Sentence(words, punctuation);
    }
}
