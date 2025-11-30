/**
 * Клас JellyCandy описує желейні цукерки.
 */
public class JellyCandy extends Sweet {

    private final String flavor; // смак, наприклад "полуниця", "виноград"

    public JellyCandy(String name,
                      double weight,
                      double price,
                      double chocolatePercent,
                      String flavor) {
        super(name, weight, price, chocolatePercent);

        if (flavor == null || flavor.isBlank()) {
            throw new IllegalArgumentException("Смак не може бути порожнім.");
        }
        this.flavor = flavor;
    }

    public String getFlavor() {
        return flavor;
    }

    @Override
    public String getType() {
        return "Желейна цукерка";
    }

    @Override
    public String toString() {
        return super.toString() + ", смак='" + flavor + '\'';
    }
}
