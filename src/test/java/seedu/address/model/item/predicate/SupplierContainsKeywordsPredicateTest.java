package seedu.address.model.item.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ItemBuilder;

public class SupplierContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SupplierContainsKeywordsPredicate firstPredicate = new SupplierContainsKeywordsPredicate(
                firstPredicateKeywordList);
        SupplierContainsKeywordsPredicate secondPredicate = new SupplierContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SupplierContainsKeywordsPredicate firstPredicateCopy = new SupplierContainsKeywordsPredicate(
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
    public void test_supplierContainsKeywords_returnsTrue() {
        // One keyword
        SupplierContainsKeywordsPredicate predicate =
                new SupplierContainsKeywordsPredicate(Collections.singletonList("NTUC"));
        assertTrue(predicate.test(new ItemBuilder().withSupplier("NTUC").build()));

        // Multiple keywords
        predicate = new SupplierContainsKeywordsPredicate(Arrays.asList("Sheng", "Siong"));
        assertTrue(predicate.test(new ItemBuilder().withSupplier("Sheng Siong").build()));

        // Only one matching keyword
        predicate = new SupplierContainsKeywordsPredicate(Arrays.asList("Sheng", "NTUC"));
        assertTrue(predicate.test(new ItemBuilder().withSupplier("Sheng Siong").build()));

        // Mixed-case keywords
        predicate = new SupplierContainsKeywordsPredicate(Arrays.asList("Cold", "Storage"));
        assertTrue(predicate.test(new ItemBuilder().withSupplier("Cold Storage").build()));
    }

    @Test
    public void test_supplierDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SupplierContainsKeywordsPredicate predicate = new SupplierContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new ItemBuilder().withSupplier("NTUC").build()));

        // Non-matching keyword
        predicate = new SupplierContainsKeywordsPredicate(Arrays.asList("NTUC"));
        assertFalse(predicate.test(new ItemBuilder().withSupplier("Cold Storage").build()));

        // Keywords match name and tag, but does not match supplier
        predicate = new SupplierContainsKeywordsPredicate(Arrays.asList("Chicken", "meat"));
        assertFalse(predicate.test(new ItemBuilder().withName("Chicken").withTags("meat")
                .build()));
    }

}
