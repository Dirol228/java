/**
 * Клас Sentence представляє речення,
 * що складається зі слів та розділових знаків.
 */
public class Sentence {

    private final Word[] words;
    private final PunctuationMark punctuation;

    public Sentence(Word[] words, PunctuationMark punctuation) {
        this.words = words;
        this.punctuation = punctuation;
    }

    public Word[] getWords() {
        return words;
    }

    public PunctuationMark getPunctuation() {
        return punctuation;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            build.append(words[i]);

            if (i != words.length - 1) {
                build.append(" ");
            }
        }

        if (punctuation != null) {
            build.append(punctuation.getValue());
        }

        return build.toString();
    }
}
