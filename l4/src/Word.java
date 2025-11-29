/**
 * Клас Word представляє слово, яке складається з масиву літер.
 * Також містить метод для обробки слова за правилами ЛР2.
 */
public class Word {

    private Letter[] letters;

    public Word(Letter[] letters) {
        this.letters = letters;
    }

    public Letter[] getLetters() {
        return letters;
    }

    /**
     * Виконує обробку слова згідно ЛР2:
     * видаляє всі повторні входження першої літери слова.
     */
    public void process() {
        if (letters.length == 0) {
            return;
        }

        char first = Character.toLowerCase(letters[0].getValue());

        // Рахуємо скільки буде нових символів
        int count = 1;
        for (int i = 1; i < letters.length; i++) {
            if (Character.toLowerCase(letters[i].getValue()) != first) {
                count++;
            }
        }

        // Формуємо новий масив
        Letter[] processed = new Letter[count];
        processed[0] = letters[0];

        int index = 1;
        for (int i = 1; i < letters.length; i++) {
            if (Character.toLowerCase(letters[i].getValue()) != first) {
                processed[index++] = letters[i];
            }
        }

        letters = processed;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        for (Letter letter : letters) {
            build.append(letter.getValue());
        }
        return build.toString();
    }
}
