/**
 * Клас PunctuationMark представляє один розділовий знак.
 */
public class PunctuationMark {

    private final char value;

    public PunctuationMark(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static boolean isPunctuation(char c) {
        return c == '.' || c == ',' || c == '!' || c == '?' || c == ':' || c == ';';
    }
}
