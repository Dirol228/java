/**
 * Виняток, що сигналізує про некоректно заданий діапазон вмісту шоколаду.
 */
public class InvalidChocolateRangeException extends Exception {

    public InvalidChocolateRangeException(String message) {
        super(message);
    }
}
