/**
 * Шоколадна цукерка.
 */
public class ChocolateCandy extends Sweet {

    private final String cocoaType;

    public ChocolateCandy(String name, double weight, double price,
                          double chocolatePercent, String cocoaType) {
        super(name, weight, price, chocolatePercent);

        if (cocoaType == null || cocoaType.isBlank()) {
            throw new IllegalArgumentException("Тип какао не може бути порожнім.");
        }
        this.cocoaType = cocoaType;
    }

    @Override
    public String getType() {
        return "Шоколадна цукерка";
    }

    @Override
    public String toString() {
        return super.toString() + ", тип какао='" + cocoaType + "'";
    }
}
