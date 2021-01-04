package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.item.exceptions.OverflowQuantityException;

/**
 * Represents a Item's quantity in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity implements Comparable<Quantity> {

    public static final String MESSAGE_CONSTRAINTS =
            "Quantity should only contain numbers, and it should be between 1-9 digits long, \n"
            + "and it should be greater than or equals to 0";
    public static final String MESSAGE_INVALID_QUANTITY_REMOVED =
            "Quantity removed should be less than the available quantity";
    public static final String MESSAGE_CONSTRAINTS_MAX_QUANTITY =
            "Max Quantity should only contain numbers, it should be between 1-9 digits long, \n"
            + "and it should be greater than 0";
    public static final String VALIDATION_REGEX = "\\d{1,9}";
    public static final String VALIDATION_REGEX_MAX_QUANTITY = "\\d{1,9}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param quantity A valid phone number.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidMaxQuantity(String test) {
        return test.matches(VALIDATION_REGEX_MAX_QUANTITY);
    }

    /**
     * adds a Quantity's value to another quantity's value
     * @param quantity another quantity
     * @return Quantity of both quantity's value added up
     */
    public Quantity add(Quantity quantity) throws OverflowQuantityException {
        int value = Integer.parseInt(this.value) + Integer.parseInt(quantity.value);
        String qty = Integer.toString(value);
        if (isValidQuantity(qty)) {
            return new Quantity(qty);
        } else {
            throw new OverflowQuantityException(qty);
        }
    }

    /**
     * subtracts a Quantity's value from another quantity's value
     * @param quantity another quantity
     * @return Quantity after subtraction
     */
    public Quantity subtract(Quantity quantity) {
        int value = Integer.parseInt(this.value) - Integer.parseInt(quantity.value);
        return new Quantity(Integer.toString(value));
    }

    /**
     * divides a Quantity's value from another quantity's value
     * @param quantity another quantity
     * @return double that represents the fraction after division
     */
    public double divideBy(Quantity quantity) {
        return Double.parseDouble(this.value) / Double.parseDouble(quantity.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Quantity quantity) {
        return Double.compare(Double.parseDouble(value), Double.parseDouble(quantity.value));
    }
}
