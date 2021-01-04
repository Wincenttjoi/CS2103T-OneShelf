package seedu.address.model.item.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.item.Item;
import seedu.address.model.item.Tag;

/**
 * Tests that a {@code Item}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Item> {

    private final List<String> keywords;

    /**
     * Constructor for SupplierContainsKeywordsPredicate.
     * @param keywords List of keywords
     */
    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        StringBuilder tags = new StringBuilder();
        for (Tag tag : item.getTags()) {
            tags.append(tag).append(" ");
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tags.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
