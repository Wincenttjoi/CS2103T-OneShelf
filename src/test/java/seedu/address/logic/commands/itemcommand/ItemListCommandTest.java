package seedu.address.logic.commands.itemcommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showItemAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ItemListCommand.
 */
public class ItemListCommandTest {

    private InventoryModel inventoryModel;
    private InventoryModel expectedInventoryModel;

    @BeforeEach
    public void setUp() {
        inventoryModel = new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());
        expectedInventoryModel = new InventoryModelManager(inventoryModel.getInventoryBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ItemListCommand(), inventoryModel, ItemListCommand.MESSAGE_SUCCESS,
                expectedInventoryModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showItemAtIndex(inventoryModel, INDEX_FIRST_ITEM);
        assertCommandSuccess(new ItemListCommand(), inventoryModel, ItemListCommand.MESSAGE_SUCCESS,
                expectedInventoryModel);
    }
}
