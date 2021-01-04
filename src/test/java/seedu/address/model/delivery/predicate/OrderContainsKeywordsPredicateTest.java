package seedu.address.model.delivery.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class OrderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        OrderContainsKeywordsPredicate firstPredicate = new OrderContainsKeywordsPredicate(
                firstPredicateKeywordList);
        OrderContainsKeywordsPredicate secondPredicate = new OrderContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderContainsKeywordsPredicate firstPredicateCopy = new OrderContainsKeywordsPredicate(
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
    public void test_ordersContainsKeywordsPredicate_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
                new OrderContainsKeywordsPredicate(Collections.singletonList("nasi"));
        assertTrue(predicate.test(new DeliveryBuilder().withOrder("nasi ayam").build()));

        // Mixed-case keywords
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("nasi lemak"));
        assertTrue(predicate.test(new DeliveryBuilder().withOrder("nasi lemak").build()));
    }

    @Test
    public void test_ordersDoesNotContainKeywordsPredicate_returnsFalse() {
        // Zero keywords
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new DeliveryBuilder().withOrder("meat").build()));

        // Non-matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("fish"));
        assertFalse(predicate.test(new DeliveryBuilder().withOrder("meat").build()));

        // Keywords match name and phone, but not order
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Kelvin", "fish"));
        assertFalse(predicate.test(new DeliveryBuilder().withName("Kelvin").withOrder("meat")
                .build()));
    }
}
