package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Delivery's order in the delivery book.
 * Guarantees: immutable; is always valid
 */
public class Order {

    public static final String MESSAGE_CONSTRAINTS = "Order can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Order}.
     *
     * @param order A valid order.
     */
    public Order(String order) {
        requireNonNull(order);
        checkArgument(isValidOrder(order), MESSAGE_CONSTRAINTS);
        value = order;
    }

    /**
     * Returns true if a given string is a valid order.
     */
    public static boolean isValidOrder(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Order // instanceof handles nulls
                && value.equals(((Order) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
