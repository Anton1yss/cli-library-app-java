package exceptions;

public class NotExistenceChoice extends RuntimeException {
    public NotExistenceChoice(int choice) {
        super("Enum cannot cast choice: " + choice);
    }

    public NotExistenceChoice(String choice) {
        super("Enum cannot cast choice: " + choice);
    }
}
