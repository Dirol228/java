import ua.kpi.comsys.test2.implementation.NumberListImpl;

public class Test {
    public static void main(String[] args) {
        NumberListImpl a = new NumberListImpl("1234"); // decimal string
        System.out.println("A (dec): " + a.toDecimalString());
        System.out.println("A (hex): " + a.toString());

        NumberListImpl b = new NumberListImpl("20");
        System.out.println("B (dec): " + b.toDecimalString());

        // Additional operation: addition (your variant)
        NumberListImpl c = a.additionalOperation(b);
        System.out.println("A + B (dec): " + c.toDecimalString());
        System.out.println("A + B (hex): " + c.toString());

        // Change scale (hex -> binary)
        NumberListImpl bin = a.changeScale();
        System.out.println("A in binary: " + bin.toString());
    }
}
