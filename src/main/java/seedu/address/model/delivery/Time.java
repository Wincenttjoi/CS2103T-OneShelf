package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Delivery's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_CONSTRAINTS =
            "Time is in minutes, should only contain numbers, and it should be at least 1 digit long, \n"
                    + "and it should be greater than or equals to 0";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public static final String TIME_FORMAT_PATTERN = "dd MMMM yyyy HH:mm:ss SSS";
    public final String minutes;
    public final LocalDateTime endTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param minutes A valid time in minutes.
     * @param endTime A LocalDateTime that the delivery should be done by.
     */
    public Time(String minutes, String endTime) {
        requireNonNull(minutes, endTime);

        LocalDateTime time = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
        this.minutes = minutes;
        this.endTime = time;
    }

    /**
     * @param minutes A valid time in minutes.
     * @return A new Time, with endTime {@code minutes} from time now.
     */
    public static Time timeFromMinutes(String minutes) {
        checkArgument(isValidTime(minutes), MESSAGE_CONSTRAINTS);
        long min = Long.parseLong(minutes);
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(min);

        return new Time(minutes, endTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN)));
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return endTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && endTime.equals(((Time) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }

    @Override
    public int compareTo(Time time) {
        return endTime.compareTo(time.endTime);
    }
}
