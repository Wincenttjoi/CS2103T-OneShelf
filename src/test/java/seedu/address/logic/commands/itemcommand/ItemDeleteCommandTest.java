package seedu.address.logic.commands.itemcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showItemAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.UserPrefs;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.item.Item;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ItemDeleteCommand}.
 */
public class ItemDeleteCommandTest {

    private InventoryModel inventoryModel = new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Item itemToDelete = inventoryModel.getFilteredAndSortedItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        ItemDeleteCommand deleteCommand = new ItemDeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(ItemDeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        InventoryModelManager expectedModel =
                new InventoryModelManager(inventoryModel.getInventoryBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);

        assertCommandSuccess(deleteCommand, inventoryModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(inventoryModel.getFilteredAndSortedItemList().size() + 1);
        ItemDeleteCommand deleteCommand = new ItemDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, inventoryModel, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(inventoryModel, INDEX_FIRST_ITEM);

        Item itemToDelete = inventoryModel.getFilteredAndSortedItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        ItemDeleteCommand deleteCommand = new ItemDeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(ItemDeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        InventoryModel expectedInventoryModel =
                new InventoryModelManager(inventoryModel.getInventoryBook(), new UserPrefs());
        expectedInventoryModel.deleteItem(itemToDelete);
        showNoItem(expectedInventoryModel);

        assertCommandSuccess(deleteCommand, inventoryModel, expectedMessage, expectedInventoryModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(inventoryModel, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of inventory book list
        assertTrue(outOfBoundIndex.getZeroBased() < inventoryModel.getInventoryBook().getItemList().size());

        ItemDeleteCommand deleteCommand = new ItemDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, inventoryModel, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ItemDeleteCommand deleteFirstCommand = new ItemDeleteCommand(INDEX_FIRST_ITEM);
        ItemDeleteCommand deleteSecondCommand = new ItemDeleteCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        ItemDeleteCommand deleteFirstCommandCopy = new ItemDeleteCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoItem(InventoryModel inventoryModel) {
        inventoryModel.updateItemListFilter(p -> false);

        assertTrue(inventoryModel.getFilteredAndSortedItemList().isEmpty());
    }
}
