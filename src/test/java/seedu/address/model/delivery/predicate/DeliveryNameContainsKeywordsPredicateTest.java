package seedu.address.model.delivery.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DeliveryNameContainsKeywordsPredicate firstPredicate = new DeliveryNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        DeliveryNameContainsKeywordsPredicate secondPredicate = new DeliveryNameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliveryNameContainsKeywordsPredicate firstPredicateCopy = new DeliveryNameContainsKeywordsPredicate(
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
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        DeliveryNameContainsKeywordsPredicate predicate =
                new DeliveryNameContainsKeywordsPredicate(Collections.singletonList("Damith"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Damith Aaron").build()));

        // Multiple keywords
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("Damith", "Aaron"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Chicken Aaron").build()));

        // Only one matching keyword
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("Damith", "Aaron"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Aaron Lim").build()));

        // Mixed-case keywords
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("Aaron", "Damith"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Aaron Damith").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DeliveryNameContainsKeywordsPredicate predicate = new DeliveryNameContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new DeliveryBuilder().withName("Damith").build()));

        // Non-matching keyword
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("Wilson"));
        assertFalse(predicate.test(new DeliveryBuilder().withName("Damith Sanjaya").build()));
    }
}
