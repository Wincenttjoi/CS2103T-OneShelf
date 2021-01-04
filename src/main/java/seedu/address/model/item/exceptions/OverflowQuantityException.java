package seedu.address.model.item.exceptions;

public class OverflowQuantityException extends Exception {
    public OverflowQuantityException(String msg) {
        super(msg);
    }
}
