package seedu.address.logic.commands.deliverycommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AARON;
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
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.EditDeliveryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * DeliveryEditCommand.
 */
public class DeliveryEditCommandTest {

    private DeliveryModel deliveryModel = new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Delivery editedDelivery = new DeliveryBuilder().build();
        DeliveryEditCommand.EditDeliveryDescriptor descriptor =
                new EditDeliveryDescriptorBuilder(editedDelivery).build();
        DeliveryEditCommand editCommand = new DeliveryEditCommand(INDEX_SECOND_ITEM, descriptor);

        String expectedMessage = String.format(DeliveryEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedDelivery);

        DeliveryModel expectedDeliveryModel =
                new DeliveryModelManager(new DeliveryBook(deliveryModel.getDeliveryBook()), new UserPrefs());
        expectedDeliveryModel.setDelivery(deliveryModel.getFilteredAndSortedDeliveryList().get(1), editedDelivery);

        assertCommandSuccess(editCommand, deliveryModel, expectedMessage, expectedDeliveryModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDelivery = Index.fromOneBased(deliveryModel.getFilteredAndSortedDeliveryList().size());
        Delivery lastDelivery = deliveryModel.getFilteredAndSortedDeliveryList().get(indexLastDelivery.getZeroBased());

        DeliveryBuilder deliveryInList = new DeliveryBuilder(lastDelivery);
        Delivery editedDelivery = deliveryInList
                .withName(VALID_NAME_AARON)
                .withPhone(VALID_PHONE_AARON)
                .withOrder(VALID_ORDER_AARON)
                .build();

        DeliveryEditCommand.EditDeliveryDescriptor descriptor =
                new EditDeliveryDescriptorBuilder()
                        .withName(VALID_NAME_AARON)
                        .withPhone(VALID_PHONE_AARON)
                        .withOrder(VALID_ORDER_AARON).build();
        DeliveryEditCommand editCommand = new DeliveryEditCommand(indexLastDelivery, descriptor);

        String expectedMessage = String.format(DeliveryEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedDelivery);

        DeliveryModel expectedDeliveryModel =
                new DeliveryModelManager(new DeliveryBook(deliveryModel.getDeliveryBook()), new UserPrefs());
        expectedDeliveryModel.setDelivery(lastDelivery, editedDelivery);

        assertCommandSuccess(editCommand, deliveryModel, expectedMessage, expectedDeliveryModel);

    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        DeliveryEditCommand editCommand = new DeliveryEditCommand(INDEX_FIRST_ITEM,
                new DeliveryEditCommand.EditDeliveryDescriptor());
        Delivery editedDelivery = deliveryModel.getFilteredAndSortedDeliveryList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(DeliveryEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedDelivery);

        DeliveryModel expectedDeliveryModel =
                new DeliveryModelManager(new DeliveryBook(deliveryModel.getDeliveryBook()), new UserPrefs());

        assertCommandSuccess(editCommand, deliveryModel, expectedMessage, expectedDeliveryModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDeliveryAtIndex(deliveryModel, INDEX_FIRST_ITEM);

        Delivery deliveryInFilteredList =
                deliveryModel.getFilteredAndSortedDeliveryList().get(INDEX_FIRST_ITEM.getZeroBased());

        Delivery editedDelivery = new DeliveryBuilder(deliveryInFilteredList)
                .withName(VALID_NAME_AARON)
                .build();

        DeliveryEditCommand editCommand = new DeliveryEditCommand(INDEX_FIRST_ITEM,
                new EditDeliveryDescriptorBuilder()
                        .withName(VALID_NAME_AARON)
                        .build());

        String expectedMessage = String.format(DeliveryEditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedDelivery);

        DeliveryModel expectedDeliveryModel =
                new DeliveryModelManager(new DeliveryBook(deliveryModel.getDeliveryBook()), new UserPrefs());

        expectedDeliveryModel.setDelivery(deliveryModel.getFilteredAndSortedDeliveryList().get(0), editedDelivery);

        assertCommandSuccess(editCommand, deliveryModel, expectedMessage, expectedDeliveryModel);
    }

    @Test
    public void execute_invalidDeliveryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(deliveryModel.getFilteredAndSortedDeliveryList().size() + 1);

        DeliveryEditCommand.EditDeliveryDescriptor descriptor = new EditDeliveryDescriptorBuilder()
                .withName(VALID_NAME_AARON)
                .build();

        DeliveryEditCommand editCommand = new DeliveryEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, deliveryModel, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of delivery book
     */
    @Test
    public void execute_invalidDeliveryIndexFilteredList_failure() {
        showDeliveryAtIndex(deliveryModel, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of inventory book list
        assertTrue(outOfBoundIndex.getZeroBased() < deliveryModel.getDeliveryBook().getDeliveryList().size());

        DeliveryEditCommand editCommand = new DeliveryEditCommand(outOfBoundIndex,
                new EditDeliveryDescriptorBuilder().withName(VALID_NAME_AARON).build());

        assertCommandFailure(editCommand, deliveryModel, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeliveryEditCommand standardCommand = new DeliveryEditCommand(INDEX_FIRST_ITEM, DESC_AARON);

        // same values -> returns true
        DeliveryEditCommand.EditDeliveryDescriptor copyDescriptor =
                new DeliveryEditCommand.EditDeliveryDescriptor(DESC_AARON);
        DeliveryEditCommand commandWithSameValues = new DeliveryEditCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new DeliveryClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeliveryEditCommand(INDEX_SECOND_ITEM, DESC_AARON)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeliveryEditCommand(INDEX_FIRST_ITEM, DESC_DAMITH)));
    }

}
