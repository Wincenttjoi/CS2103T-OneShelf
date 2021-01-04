package seedu.address.logic.commands.deliverycommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.ModelsManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.testutil.DeliveryBuilder;

public class DeliveryAddCommandTest {

    @Test
    public void constructor_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryAddCommand(null));
    }

    @Test
    public void execute_deliveryAcceptedByModel_addSuccessful() throws CommandException {
        DeliveryAddCommandTest.DeliveryModelStubAcceptingDeliveriesAdded modelStub =
                new DeliveryAddCommandTest.DeliveryModelStubAcceptingDeliveriesAdded();
        Models models = new ModelsManager(new InventoryModelManager(), modelStub);
        Delivery validDelivery = new DeliveryBuilder().build();


        CommandResult commandResult = new DeliveryAddCommand(validDelivery).execute(models);

        assertEquals(String.format(DeliveryAddCommand.MESSAGE_SUCCESS, validDelivery),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDelivery), modelStub.deliveryAdded);
    }

    @Test
    public void equals() {
        Delivery alice = new DeliveryBuilder().withName("Alice").build();
        Delivery bob = new DeliveryBuilder().withName("Bob").build();
        DeliveryAddCommand addAliceCommand = new DeliveryAddCommand(alice);
        DeliveryAddCommand addBobCommand = new DeliveryAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        DeliveryAddCommand addAliceCommandCopy = new DeliveryAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different deliveries -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class DeliveryModelStub implements DeliveryModel {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatesLimit(int limit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commit() {
            return;
        }
        @Override
        public void undo() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void redo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDeliveryBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDelivery(Delivery delivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeliveryBook(ReadOnlyDeliveryBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDeliveryBook getDeliveryBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDelivery(Delivery target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDelivery(Delivery target, Delivery editedDelivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getFilteredAndSortedDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
    /**
     * A Model stub that always accept the delivery being added.
     */
    private class DeliveryModelStubAcceptingDeliveriesAdded extends DeliveryAddCommandTest.DeliveryModelStub {
        final ArrayList<Delivery> deliveryAdded = new ArrayList<>();

        @Override
        public void addDelivery(Delivery delivery) {
            requireNonNull(delivery);
            deliveryAdded.add(delivery);
        }

        @Override
        public ReadOnlyDeliveryBook getDeliveryBook() {
            return new DeliveryBook();
        }
    }
}
