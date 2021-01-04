package seedu.address.logic.commands.itemcommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.item.Item;
import seedu.address.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code ItemAddCommand}.
 */
public class ItemAddCommandIntegrationTest {

    private InventoryModel inventoryModel;

    @BeforeEach
    public void setUp() {
        inventoryModel = new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());
    }

    @Test
    public void execute_newItem_success() {
        Item validItem = new ItemBuilder().build();

        InventoryModel expectedInventoryModel =
                new InventoryModelManager(inventoryModel.getInventoryBook(), new UserPrefs());
        expectedInventoryModel.addItem(validItem);

        assertCommandSuccess(new ItemAddCommand(validItem), inventoryModel,
                String.format(seedu.address.logic.commands.itemcommand.ItemAddCommand.MESSAGE_SUCCESS, validItem),
                expectedInventoryModel);
    }
}
