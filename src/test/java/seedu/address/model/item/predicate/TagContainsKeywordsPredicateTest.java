package seedu.address.model.item.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ItemBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(
                firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different Item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("meat"));
        assertTrue(predicate.test(new ItemBuilder().withTags("meat", "premium").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Meat"));
        assertTrue(predicate.test(new ItemBuilder().withTags("meat", "premium").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new ItemBuilder().withTags("meat").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("fish"));
        assertFalse(predicate.test(new ItemBuilder().withTags("meat").build()));

        // Keywords match name and supplier, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Chicken", "NTUC"));
        assertFalse(predicate.test(new ItemBuilder().withName("Chicken").withSupplier("NTUC")
                .build()));
    }
}
