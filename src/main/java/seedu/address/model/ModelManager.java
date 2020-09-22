package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.item.Item;

/**
 * Represents the in-memory model of the inventory book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final InventoryBook inventoryBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Item> filteredItems;

    /**
     * Initializes a ModelManager with the given inventoryBook and userPrefs.
     */
    public ModelManager(ReadOnlyInventoryBook inventoryBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(inventoryBook, userPrefs);

        logger.fine("Initializing with inventory book: " + inventoryBook + " and user prefs " + userPrefs);

        this.inventoryBook = new InventoryBook(inventoryBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredItems = new FilteredList<>(this.inventoryBook.getItemList());
    }

    public ModelManager() {
        this(new InventoryBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public Path getInventoryBookFilePath() {
        return userPrefs.getInventoryBookFilePath();
    }

    @Override
    public void setInventoryBookFilePath(Path inventoryBookFilePath) {
        requireNonNull(inventoryBookFilePath);
        userPrefs.setInventoryBookFilePath(inventoryBookFilePath);
    }

    //=========== InventoryBook ================================================================================

    @Override
    public void setInventoryBook(ReadOnlyInventoryBook inventoryBook) {
        this.inventoryBook.resetData(inventoryBook);
    }

    @Override
    public ReadOnlyInventoryBook getInventoryBook() {
        return inventoryBook;
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return inventoryBook.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        inventoryBook.removeItem(target);
    }

    @Override
    public void addItem(Item item) {
        inventoryBook.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public Item addOnExistingItem(Item item) {
        Item combinedItem = inventoryBook.addOnExistingItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return combinedItem;
    }

    @Override
    public void setItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        inventoryBook.setItem(target, editedItem);
    }

    //=========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedInventoryBook}
     */
    @Override
    public ObservableList<Item> getFilteredItemList() {
        return filteredItems;
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return inventoryBook.equals(other.inventoryBook)
                && userPrefs.equals(other.userPrefs)
                && filteredItems.equals(other.filteredItems);
    }

}
