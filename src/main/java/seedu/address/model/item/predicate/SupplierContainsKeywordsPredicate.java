package seedu.address.model.item.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.item.Item;

/**
 * Tests that a {@code Item}'s {@code Supplier} matches any of the keywords given.
 */
public class SupplierContainsKeywordsPredicate implements Predicate<Item> {

    private final List<String> keywords;

    /**
     * Constructor for SupplierContainsKeywordsPredicate.
     *
     * @param keywords List of keywords
     */
    public SupplierContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(item.getSupplier().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SupplierContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SupplierContainsKeywordsPredicate) other).keywords)); // state check
    }

}
