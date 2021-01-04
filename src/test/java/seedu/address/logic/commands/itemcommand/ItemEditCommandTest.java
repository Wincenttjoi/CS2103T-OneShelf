package seedu.address.logic.commands.itemcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DUCK;
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
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.item.Item;
import seedu.address.testutil.EditItemDescriptorBuilder;
import seedu.address.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * ItemEditCommand.
 */
public class ItemEditCommandTest {

    private InventoryModel inventoryModel = new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Item editedItem = new ItemBuilder().build();
        ItemEditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        ItemEditCommand editCommand = new ItemEditCommand(INDEX_SECOND_ITEM, descriptor);

        String expectedMessage = String.format(ItemEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        InventoryModel expectedInventoryModel =
                new InventoryModelManager(new InventoryBook(inventoryModel.getInventoryBook()), new UserPrefs());
        expectedInventoryModel.setItem(inventoryModel.getFilteredAndSortedItemList().get(1), editedItem);

        assertCommandSuccess(editCommand, inventoryModel, expectedMessage, expectedInventoryModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastItem = Index.fromOneBased(inventoryModel.getFilteredAndSortedItemList().size());
        Item lastItem = inventoryModel.getFilteredAndSortedItemList().get(indexLastItem.getZeroBased());

        ItemBuilder itemInList = new ItemBuilder(lastItem);
        Item editedItem = itemInList.withName(VALID_NAME_DUCK).withQuantity(VALID_QUANTITY_DUCK)
                .withTags(VALID_TAG_DUCK).build();

        ItemEditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_DUCK)
                .withQuantity(VALID_QUANTITY_DUCK).withTags(VALID_TAG_DUCK).build();
        ItemEditCommand editCommand = new ItemEditCommand(indexLastItem, descriptor);

        String expectedMessage = String.format(ItemEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        InventoryModel expectedInventoryModel =
                new InventoryModelManager(new InventoryBook(inventoryModel.getInventoryBook()), new UserPrefs());
        expectedInventoryModel.setItem(lastItem, editedItem);

        assertCommandSuccess(editCommand, inventoryModel, expectedMessage, expectedInventoryModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ItemEditCommand editCommand = new ItemEditCommand(INDEX_FIRST_ITEM, new ItemEditCommand.EditItemDescriptor());
        Item editedItem = inventoryModel.getFilteredAndSortedItemList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(ItemEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        InventoryModel expectedInventoryModel =
                new InventoryModelManager(new InventoryBook(inventoryModel.getInventoryBook()), new UserPrefs());

        assertCommandSuccess(editCommand, inventoryModel, expectedMessage, expectedInventoryModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(inventoryModel, INDEX_FIRST_ITEM);

        Item itemInFilteredList = inventoryModel.getFilteredAndSortedItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        Item editedItem = new ItemBuilder(itemInFilteredList).withName(VALID_NAME_CHICKEN).build();
        ItemEditCommand editCommand = new ItemEditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder().withName(VALID_NAME_CHICKEN).build());

        String expectedMessage = String.format(ItemEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        InventoryModel expectedInventoryModel =
                new InventoryModelManager(new InventoryBook(inventoryModel.getInventoryBook()), new UserPrefs());
        expectedInventoryModel.setItem(inventoryModel.getFilteredAndSortedItemList().get(0), editedItem);

        assertCommandSuccess(editCommand, inventoryModel, expectedMessage, expectedInventoryModel);
    }

    @Test
    public void execute_duplicateItemUnfilteredList_failure() {
        Item firstItem = inventoryModel.getFilteredAndSortedItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        ItemEditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder(firstItem).build();
        ItemEditCommand editCommand = new ItemEditCommand(INDEX_SECOND_ITEM, descriptor);

        assertCommandFailure(editCommand, inventoryModel, ItemEditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_duplicateItemFilteredList_failure() {
        showItemAtIndex(inventoryModel, INDEX_FIRST_ITEM);

        // edit item in filtered list into a duplicate in inventory book
        Item itemInList = inventoryModel.getInventoryBook().getItemList().get(INDEX_SECOND_ITEM.getZeroBased());

        ItemEditCommand editCommand = new ItemEditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder(itemInList).build());

        assertCommandFailure(editCommand, inventoryModel, ItemEditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(inventoryModel.getFilteredAndSortedItemList().size() + 1);
        ItemEditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder()
                .withName(VALID_NAME_DUCK).build();
        ItemEditCommand editCommand = new ItemEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, inventoryModel, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of inventory book
     */
    @Test
    public void execute_invalidItemIndexFilteredList_failure() {
        showItemAtIndex(inventoryModel, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of inventory book list
        assertTrue(outOfBoundIndex.getZeroBased() < inventoryModel.getInventoryBook().getItemList().size());

        ItemEditCommand editCommand = new ItemEditCommand(outOfBoundIndex,
                new EditItemDescriptorBuilder().withName(VALID_NAME_DUCK).build());

        assertCommandFailure(editCommand, inventoryModel, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ItemEditCommand standardCommand = new ItemEditCommand(INDEX_FIRST_ITEM, DESC_CHICKEN);

        // same values -> returns true
        ItemEditCommand.EditItemDescriptor copyDescriptor = new ItemEditCommand.EditItemDescriptor(DESC_CHICKEN);
        ItemEditCommand commandWithSameValues = new ItemEditCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ItemClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ItemEditCommand(INDEX_SECOND_ITEM, DESC_CHICKEN)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ItemEditCommand(INDEX_FIRST_ITEM, DESC_DUCK)));
    }

}
