package seedu.address.logic.commands.itemcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.item.Item;
import seedu.address.model.item.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.item.predicate.SupplierContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ItemFindCommand}.
 */
public class ItemFindCommandTest {

    private InventoryModel inventoryModel = new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());
    private InventoryModel expectedInventoryModel =
            new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        ItemFindCommand findFirstCommand = new ItemFindCommand(firstPredicate);
        ItemFindCommand findSecondCommand = new ItemFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ItemFindCommand findFirstCommandCopy = new ItemFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noItemFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        ItemFindCommand command = new ItemFindCommand(predicate);
        expectedInventoryModel.updateItemListFilter(predicate);
        assertCommandSuccess(command, inventoryModel, expectedMessage, expectedInventoryModel);
        assertEquals(Collections.emptyList(), inventoryModel.getFilteredAndSortedItemList());
    }

    @Test
    public void execute_twoFieldSpecified_itemFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 1);
        Predicate<Item> predicate = prepareNamePredicate("Chicken")
                .and(prepareSupplierPredicate("GIANT"));
        ItemFindCommand command = new ItemFindCommand(predicate);
        expectedInventoryModel.updateItemListFilter(predicate);
        assertCommandSuccess(command, inventoryModel, expectedMessage, expectedInventoryModel);
        assertEquals(expectedInventoryModel.getFilteredAndSortedItemList(),
                inventoryModel.getFilteredAndSortedItemList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code SupplierContainsKeywordsPredicate}.
     */
    private SupplierContainsKeywordsPredicate prepareSupplierPredicate(String userInput) {
        return new SupplierContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


}
