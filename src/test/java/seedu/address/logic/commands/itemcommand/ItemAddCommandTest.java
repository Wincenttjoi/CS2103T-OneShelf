package seedu.address.logic.commands.itemcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.ModelsManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.model.item.Item;
import seedu.address.model.item.Metric;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.item.Tag;
import seedu.address.model.item.exceptions.OverflowQuantityException;
import seedu.address.testutil.ItemBuilder;

public class ItemAddCommandTest {

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemAddCommand(null));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws CommandException {
        ItemModelStubAcceptingInventoryAdded modelStub = new ItemModelStubAcceptingInventoryAdded();
        Models models = new ModelsManager(modelStub, new DeliveryModelManager());
        Item validItem = new ItemBuilder().build();

        CommandResult commandResult = new ItemAddCommand(validItem).execute(models);

        assertEquals(String.format(seedu.address.logic.commands.itemcommand.ItemAddCommand.MESSAGE_SUCCESS, validItem),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validItem), modelStub.itemsAdded);
    }

    @Test
    public void execute_duplicateItem_updateQuantitySuccessful() throws CommandException {
        Item currentItem = new ItemBuilder().withName("Chicken").withQuantity("2").build();
        Item finalItem = new ItemBuilder().withName("Chicken").withQuantity("4").build();
        InventoryModelStub modelStub = new ItemModelStubAcceptingDuplicatingInventory(currentItem);
        Models models = new ModelsManager(modelStub, new DeliveryModelManager());

        CommandResult commandResult = new ItemAddCommand(currentItem).execute(models);

        assertEquals(String.format(ItemAddCommand.MESSAGE_ITEM_ADDED_TO_INVENTORY, finalItem),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateItem_updateMaxQuantityUnsuccessful() {
        Item currentItem = new ItemBuilder().withName("Chicken").withQuantity("2").withMaxQuantity("500").build();
        Item finalItem = new ItemBuilder().withName("Chicken").withQuantity("4").withMaxQuantity("5000").build();
        InventoryModelStub modelStub = new ItemModelStubAcceptingDuplicatingInventory(currentItem);
        Models models = new ModelsManager(modelStub, new DeliveryModelManager());

        assertThrows(CommandException.class, () -> new ItemAddCommand(finalItem).execute(models));
    }

    @Test
    public void execute_duplicateItem_updateMetricUnsuccessful() {
        Item currentItem = new ItemBuilder().withName("Chicken").withQuantity("2").withMaxQuantity("500").build();
        Item finalItem = new ItemBuilder().withName("Chicken").withQuantity("4").withMetric("kg").build();
        InventoryModelStub modelStub = new ItemModelStubAcceptingDuplicatingInventory(currentItem);
        Models models = new ModelsManager(modelStub, new DeliveryModelManager());

        assertThrows(CommandException.class, () -> new ItemAddCommand(finalItem).execute(models));
    }

    @Test
    public void equals() {
        Item chicken = new ItemBuilder().withName("Chicken").build();
        Item duck = new ItemBuilder().withName("Duck").build();
        ItemAddCommand addChickenCommand = new ItemAddCommand(chicken);
        ItemAddCommand addDuckCommand = new ItemAddCommand(duck);

        // same object -> returns true
        assertTrue(addChickenCommand.equals(addChickenCommand));

        // same values -> returns true
        ItemAddCommand addChickenCommandCopy = new ItemAddCommand(chicken);
        assertTrue(addChickenCommand.equals(addChickenCommandCopy));

        // different types -> returns false
        assertFalse(addChickenCommand.equals(1));

        // null -> returns false
        assertFalse(addChickenCommand.equals(null));

        // different item -> returns false
        assertFalse(addChickenCommand.equals(addDuckCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class InventoryModelStub implements InventoryModel {
        private static final String MESSAGE_METHOD_SHOULD_NOT_BE_CALLED = "This method should not be called.";
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void setStatesLimit(int limit) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public Path getInventoryBookFilePath() {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void setInventoryBookFilePath(Path inventoryBookFilePath) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void addItem(Item item) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public Item addOnExistingItem(Item item) throws OverflowQuantityException {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void setInventoryBook(ReadOnlyInventoryBook newData) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public ReadOnlyInventoryBook getInventoryBook() {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public boolean hasItem(Item item) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void deleteItem(Item target) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void setItem(Item target, Item editedItem) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public ObservableList<Item> getFilteredAndSortedItemList() {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void updateItemListFilter(Predicate<Item> predicate) {
            throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
        }

        @Override
        public void commit() { }

        @Override
        public void undo() { }

        @Override
        public void redo() { }
    }

    /**
     * A Model stub that accepts duplicate item, updating its quantity.
     */
    private class ItemModelStubAcceptingDuplicatingInventory extends InventoryModelStub {
        private final Item item;

        ItemModelStubAcceptingDuplicatingInventory(Item item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public Item addOnExistingItem(Item item) throws OverflowQuantityException {
            Name name = item.getName();
            Quantity quantity = item.getQuantity().add(item.getQuantity());
            Supplier supplier = item.getSupplier();
            Set<Tag> providedItemTags = item.getTags();
            Set<Tag> combinedTags = new HashSet<>(providedItemTags);
            Quantity maxQuantity = item.getMaxQuantity().orElse(null);
            Metric metric = item.getMetric().orElse(null);

            return new Item(name, quantity, supplier, combinedTags, maxQuantity, metric);
        }

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return this.item.isSameItem(item);
        }
    }

    /**
     * A Model stub that always accept the item being added.
     */
    private class ItemModelStubAcceptingInventoryAdded extends InventoryModelStub {
        final ArrayList<Item> itemsAdded = new ArrayList<>();

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return itemsAdded.stream().anyMatch(item::isSameItem);
        }

        @Override
        public void addItem(Item item) {
            requireNonNull(item);
            itemsAdded.add(item);
        }

        @Override
        public ReadOnlyInventoryBook getInventoryBook() {
            return new InventoryBook();
        }
    }

}
