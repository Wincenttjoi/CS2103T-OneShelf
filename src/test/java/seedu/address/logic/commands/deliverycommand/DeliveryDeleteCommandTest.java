package seedu.address.logic.commands.deliverycommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeliveryAtIndex;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeliveryDeleteCommand}.
 */
public class DeliveryDeleteCommandTest {

    private DeliveryModel deliveryModel = new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Delivery deliveryToDelete =
                deliveryModel.getFilteredAndSortedDeliveryList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeliveryDeleteCommand deleteCommand = new DeliveryDeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeliveryDeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, deliveryToDelete);

        DeliveryModelManager expectedModel =
                new DeliveryModelManager(deliveryModel.getDeliveryBook(), new UserPrefs());
        expectedModel.deleteDelivery(deliveryToDelete);

        assertCommandSuccess(deleteCommand, deliveryModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(deliveryModel.getFilteredAndSortedDeliveryList().size() + 1);
        DeliveryDeleteCommand deleteCommand = new DeliveryDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, deliveryModel, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeliveryAtIndex(deliveryModel, INDEX_FIRST_ITEM);

        Delivery deliveryToDelete =
                deliveryModel.getFilteredAndSortedDeliveryList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeliveryDeleteCommand deleteCommand = new DeliveryDeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeliveryDeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, deliveryToDelete);

        DeliveryModel expectedDeliveryModel =
                new DeliveryModelManager(deliveryModel.getDeliveryBook(), new UserPrefs());
        expectedDeliveryModel.deleteDelivery(deliveryToDelete);
        showNoDelivery(expectedDeliveryModel);

        assertCommandSuccess(deleteCommand, deliveryModel, expectedMessage, expectedDeliveryModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDeliveryAtIndex(deliveryModel, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of delivery book list
        assertTrue(outOfBoundIndex.getZeroBased() < deliveryModel.getDeliveryBook().getDeliveryList().size());

        DeliveryDeleteCommand deleteCommand = new DeliveryDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, deliveryModel, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeliveryDeleteCommand deleteFirstCommand = new DeliveryDeleteCommand(INDEX_FIRST_ITEM);
        DeliveryDeleteCommand deleteSecondCommand = new DeliveryDeleteCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeliveryDeleteCommand deleteFirstCommandCopy = new DeliveryDeleteCommand(INDEX_FIRST_ITEM);
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
    private void showNoDelivery(DeliveryModel deliveryModel) {
        deliveryModel.updateFilteredDeliveryList(p -> false);

        assertTrue(deliveryModel.getFilteredAndSortedDeliveryList().isEmpty());
    }
}
