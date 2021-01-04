package seedu.address.model.delivery.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.delivery.Delivery;

/**
 * Tests that a {@code Delivery}'s {@code Name} matches any of the keywords given.
 */
public class DeliveryNameContainsKeywordsPredicate implements Predicate<Delivery> {

    private final List<String> keywords;

    /**
     * Constructor for DeliveryNameContainsKeywordsPredicate.
     * @param keywords List of keywords
     */
    public DeliveryNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Delivery delivery) {
        assert(delivery != null);
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(delivery.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DeliveryNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
