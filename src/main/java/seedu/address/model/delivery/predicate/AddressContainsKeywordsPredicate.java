package seedu.address.model.delivery.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.delivery.Delivery;

/**
 * Tests that a {@code Delivery}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Delivery> {

    private final List<String> keywords;

    /**
     * Constructor for DeliveryContainsKeywordsPredicate.
     * @param keywords List of keywords
     */
    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Delivery delivery) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(delivery.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }
}
