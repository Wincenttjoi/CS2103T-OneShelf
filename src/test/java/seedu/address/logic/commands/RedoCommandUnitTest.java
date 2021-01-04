package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EARLY_TEST_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_REDO_LIMIT_REACHED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RedoCommand.MESSAGE_REDO_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
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

class RedoCommandUnitTest {

    @Test
    void execute_emptyModels_limitReached() {
        Models actualModels = new ModelsManager();
        Models expectedModels = new ModelsManager();
        assertCommandSuccess(
                new RedoCommand(), actualModels, MESSAGE_REDO_LIMIT_REACHED, expectedModels);
    }

    @Test
    void execute_nothingToRedo_limitReached() {
        Models actualModels = new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel());
        Models expectedModels = new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel());

        assertCommandSuccess(
                new RedoCommand(), actualModels, MESSAGE_REDO_LIMIT_REACHED, expectedModels);
    }

    @Test
    void execute_changedInventoryState_success() {
        InventoryModel inventoryModel1 = makeTestInventoryModel();
        InventoryModel inventoryModel2 = makeTestInventoryModel();
        Models actualModels = new ModelsManager(inventoryModel1, new DeliveryModelManager());
        Models expectedModels = new ModelsManager(inventoryModel2, new DeliveryModelManager());

        // change the state, commit, then undo
        Item testItem = new ItemBuilder().build();
        inventoryModel1.addItem(testItem);
        inventoryModel2.addItem(testItem);
        actualModels.commit();
        try {
            actualModels.undo();
        } catch (UndoRedoLimitReachedException e) {
            Assertions.fail(MESSAGE_EARLY_TEST_FAILURE);
        }

        assertCommandSuccess(
                new RedoCommand(), actualModels, MESSAGE_REDO_ACKNOWLEDGEMENT, expectedModels);
    }

    @Test
    void execute_redoAfterCommittingInventory_limitReached() {
        InventoryModel inventoryModel1 = makeTestInventoryModel();
        Models actualModels = new ModelsManager(inventoryModel1, new DeliveryModelManager());
        Models expectedModels = new ModelsManager(makeTestInventoryModel(), new DeliveryModelManager());

        // change state, commit, undo, then commit again
        Item testItem = new ItemBuilder().build();
        inventoryModel1.addItem(testItem);
        actualModels.commit();
        try {
            actualModels.undo();
        } catch (UndoRedoLimitReachedException e) {
            Assertions.fail(MESSAGE_EARLY_TEST_FAILURE);
        }
        actualModels.commit();

        assertCommandSuccess(new RedoCommand(), actualModels, MESSAGE_REDO_LIMIT_REACHED, expectedModels);
    }

    @Test
    void execute_changedDeliveryState_success() {
        DeliveryModel deliveryModel1 = makeTestDeliveryModel();
        DeliveryModel deliveryModel2 = makeTestDeliveryModel();
        Models actualModels = new ModelsManager(new InventoryModelManager(), deliveryModel1);
        Models expectedModels = new ModelsManager(new InventoryModelManager(), deliveryModel2);

        // change the state, commit, then undo
        Delivery testDelivery = new DeliveryBuilder().build();
        deliveryModel1.addDelivery(testDelivery);
        deliveryModel2.addDelivery(testDelivery);
        actualModels.commit();
        try {
            actualModels.undo();
        } catch (UndoRedoLimitReachedException e) {
            Assertions.fail(MESSAGE_EARLY_TEST_FAILURE);
        }

        assertCommandSuccess(
                new RedoCommand(), actualModels, MESSAGE_REDO_ACKNOWLEDGEMENT, expectedModels);
    }

    @Test
    void execute_changedDeliveryAndInventoryState_success() {

        InventoryModel inventoryModel1 = makeTestInventoryModel();
        InventoryModel inventoryModel2 = makeTestInventoryModel();
        DeliveryModel deliveryModel1 = makeTestDeliveryModel();
        DeliveryModel deliveryModel2 = makeTestDeliveryModel();
        Models actualModels = new ModelsManager(inventoryModel1, deliveryModel1);
        Models expectedModels = new ModelsManager(inventoryModel2, deliveryModel2);

        // change the state, commit, then undo
        Item testItem = new ItemBuilder().build();
        Delivery testDelivery = new DeliveryBuilder().build();
        inventoryModel1.addItem(testItem);
        inventoryModel2.addItem(testItem);
        deliveryModel1.addDelivery(testDelivery);
        deliveryModel2.addDelivery(testDelivery);

        actualModels.commit();
        try {
            actualModels.undo();
        } catch (UndoRedoLimitReachedException e) {
            Assertions.fail(MESSAGE_EARLY_TEST_FAILURE);
        }

        assertCommandSuccess(
                new RedoCommand(), actualModels, MESSAGE_REDO_ACKNOWLEDGEMENT, expectedModels);
    }

    @Test
    void execute_redoAfterCommittingDelivery_limitReached() {
        DeliveryModel deliveryModel1 = makeTestDeliveryModel();
        Models actualModels = new ModelsManager(new InventoryModelManager(), deliveryModel1);
        Models expectedModels = new ModelsManager(new InventoryModelManager(), makeTestDeliveryModel());

        // change state, commit, undo, then commit again
        Delivery testDelivery = new DeliveryBuilder().build();
        deliveryModel1.addDelivery(testDelivery);
        actualModels.commit();
        try {
            actualModels.undo();
        } catch (UndoRedoLimitReachedException e) {
            Assertions.fail(MESSAGE_EARLY_TEST_FAILURE);
        }
        actualModels.commit();

        assertCommandSuccess(new RedoCommand(), actualModels, MESSAGE_REDO_LIMIT_REACHED, expectedModels);
    }

    @Test
    void execute_redoAfterCommittingInventoryAndDelivery_limitReached() {
        InventoryModel inventoryModel1 = makeTestInventoryModel();
        DeliveryModel deliveryModel1 = makeTestDeliveryModel();
        Models actualModels = new ModelsManager(inventoryModel1, deliveryModel1);
        Models expectedModels = new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel());

        // change state, commit, undo, then commit again
        Item testItem = new ItemBuilder().build();
        Delivery testDelivery = new DeliveryBuilder().build();
        inventoryModel1.addItem(testItem);
        deliveryModel1.addDelivery(testDelivery);
        actualModels.commit();
        try {
            actualModels.undo();
        } catch (UndoRedoLimitReachedException e) {
            Assertions.fail(MESSAGE_EARLY_TEST_FAILURE);
        }
        actualModels.commit();

        assertCommandSuccess(new RedoCommand(), actualModels, MESSAGE_REDO_LIMIT_REACHED, expectedModels);
    }

    private InventoryModel makeTestInventoryModel() {
        return new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());
    }

    private DeliveryModel makeTestDeliveryModel() {
        return new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
    }
}
