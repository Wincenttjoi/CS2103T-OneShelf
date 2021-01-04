package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Item's metric in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMetric(String)}
 */
public class Metric {

    public static final String MESSAGE_CONSTRAINTS = "Metric should be alphabetical, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{L}][\\p{L} ]*";

    public final String value;

    /**
     * Constructs an {@code Metric}.
     *
     * @param metric A valid metric.
     */
    public Metric(String metric) {
        requireNonNull(metric);
        checkArgument(isValidMetric(metric), MESSAGE_CONSTRAINTS);
        value = metric.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid metric.
     */
    public static boolean isValidMetric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Metric // instanceof handles nulls
                && value.equals(((Metric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
