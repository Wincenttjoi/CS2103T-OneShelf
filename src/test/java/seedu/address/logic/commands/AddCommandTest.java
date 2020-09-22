package seedu.address.logic.commands;

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
import seedu.address.model.InventoryBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventoryBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ItemBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingItemAdded modelStub = new ModelStubAcceptingItemAdded();
        Item validItem = new ItemBuilder().build();

        CommandResult commandResult = new AddCommand(validItem).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validItem), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validItem), modelStub.itemsAdded);
    }

    @Test
    public void execute_duplicateItem_updateQualitySuccessful() {
        Item currentItem = new ItemBuilder().withName("Chicken").withQuantity("2").build();
        Item finalItem = new ItemBuilder().withName("Chicken").withQuantity("4").build();
        ModelStub modelStub = new ModelStubAcceptingDuplicatingItem(currentItem);

        CommandResult commandResult = new AddCommand(currentItem).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_ITEM_ADDED_TO_INVENTORY, finalItem),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Item chicken = new ItemBuilder().withName("Chicken").build();
        Item duck = new ItemBuilder().withName("Duck").build();
        AddCommand addChickenCommand = new AddCommand(chicken);
        AddCommand addDuckCommand = new AddCommand(duck);

        // same object -> returns true
        assertTrue(addChickenCommand.equals(addChickenCommand));

        // same values -> returns true
        AddCommand addChickenCommandCopy = new AddCommand(chicken);
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
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        /**
        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
         **/

        @Override
        public Path getInventoryBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventoryBookFilePath(Path inventoryBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Item addOnExistingItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventoryBook(ReadOnlyInventoryBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInventoryBook getInventoryBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItem(Item target, Item editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Item> getFilteredItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredItemList(Predicate<Item> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that accepts duplicate item, updating its quantity.
     */
    private class ModelStubAcceptingDuplicatingItem extends ModelStub {

        private final Item item;

        ModelStubAcceptingDuplicatingItem(Item item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public Item addOnExistingItem(Item item) {
            Name name = item.getName();
            Quantity quantity = item.getQuantity().add(item.getQuantity());
            Supplier supplier = item.getSupplier();
            Set<Tag> providedItemTags = item.getTags();
            Set<Tag> combinedTags = new HashSet<>(providedItemTags);

            return new Item(name, quantity, supplier, combinedTags);
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
    private class ModelStubAcceptingItemAdded extends ModelStub {
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
