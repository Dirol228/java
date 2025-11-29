/**
 * Клас Text представляє текст,
 * який складається з масиву речень.
 */
public class Text {

    private final Sentence[] sentences;

    public Text(Sentence[] sentences) {
        this.sentences = sentences;
    }

    public Sentence[] getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();

        for (Sentence s : sentences) {
            build.append(s.toString()).append(" ");
        }

        return build.toString().trim();
    }
}
