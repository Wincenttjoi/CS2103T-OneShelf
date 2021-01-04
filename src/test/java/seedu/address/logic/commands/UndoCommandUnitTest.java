package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_UNDO_LIMIT_REACHED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Models;
import seedu.address.model.ModelsManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.item.Item;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.ItemBuilder;

class UndoCommandUnitTest {

    @Test
    void execute_emptyModels_limitReached() {
        Models actualModels = new ModelsManager();
        Models expectedModels = new ModelsManager();
        assertCommandSuccess(
                new UndoCommand(), actualModels, MESSAGE_UNDO_LIMIT_REACHED, expectedModels);
    }

    @Test
    void execute_nothingToUndo_limitReached() {
        Models actualModels = new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel());
        Models expectedModels = new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel());

        assertCommandSuccess(
                new UndoCommand(), actualModels, MESSAGE_UNDO_LIMIT_REACHED, expectedModels);
    }

    @Test
    void execute_changedInventoryState_success() {
        InventoryModel inventoryModel1 = makeTestInventoryModel();
        Models actualModels = new ModelsManager(inventoryModel1, new DeliveryModelManager());
        Models expectedModels = new ModelsManager(makeTestInventoryModel(), new DeliveryModelManager());

        // change the state and commit
        Item testItem = new ItemBuilder().build();
        inventoryModel1.addItem(testItem);
        actualModels.commit();

        assertCommandSuccess(
                new UndoCommand(), actualModels, MESSAGE_UNDO_ACKNOWLEDGEMENT, expectedModels);
    }

    @Test
    void execute_changedDeliveryState_success() {
        DeliveryModel deliveryModel1 = makeTestDeliveryModel();
        Models actualModels = new ModelsManager(new InventoryModelManager(), deliveryModel1);
        Models expectedModels = new ModelsManager(new InventoryModelManager(), makeTestDeliveryModel());

        // change the state and commit
        Delivery testDelivery = new DeliveryBuilder().build();
        deliveryModel1.addDelivery(testDelivery);
        actualModels.commit();

        assertCommandSuccess(
                new UndoCommand(), actualModels, MESSAGE_UNDO_ACKNOWLEDGEMENT, expectedModels);
    }

    @Test
    void execute_changedDeliveryAndInventoryState_success() {
        DeliveryModel deliveryModel1 = makeTestDeliveryModel();
        InventoryModel inventoryModel1 = makeTestInventoryModel();
        Models actualModels = new ModelsManager(inventoryModel1, deliveryModel1);
        Models expectedModels = new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel());

        // change the state and commit
        Delivery testDelivery = new DeliveryBuilder().build();
        Item testItem = new ItemBuilder().build();
        deliveryModel1.addDelivery(testDelivery);
        inventoryModel1.addItem(testItem);
        actualModels.commit();

        assertCommandSuccess(
                new UndoCommand(), actualModels, MESSAGE_UNDO_ACKNOWLEDGEMENT, expectedModels);
    }

    private InventoryModel makeTestInventoryModel() {
        return new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());
    }

    private DeliveryModel makeTestDeliveryModel() {
        return new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
    }

}
