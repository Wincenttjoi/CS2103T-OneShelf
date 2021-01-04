package seedu.address.model.inventorymodel;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_REDO_LIMIT_REACHED;
import static seedu.address.commons.core.Messages.MESSAGE_UNDO_LIMIT_REACHED;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Item;
import seedu.address.model.item.exceptions.OverflowQuantityException;

/**
 * Represents the in-memory model of the inventory book data.
 */
public class InventoryModelManager implements InventoryModel {
    private static final Logger logger = LogsCenter.getLogger(InventoryModelManager.class);

    private List<InventoryBook> inventoryBookStateList = new ArrayList<>(MODEL_DEFAULT_STATES_LIMIT);
    private int inventoryBookStatePointer = -1;
    private int statesLimit = MODEL_DEFAULT_STATES_LIMIT;
    private final InventoryBook inventoryBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Item> filteredItems;


    /**
     * Initializes a InventoryModelManager with the given inventoryBook and userPrefs.
     */
    public InventoryModelManager(ReadOnlyInventoryBook inventoryBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(inventoryBook, userPrefs);

        logger.fine("Initializing with inventory book: " + inventoryBook + " and user prefs " + userPrefs);

        this.inventoryBook = new InventoryBook(inventoryBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredItems = new FilteredList<>(this.inventoryBook.getItemList());
    }

    public InventoryModelManager() {
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
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
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
        updateItemListFilter(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public Item addOnExistingItem(Item item) throws OverflowQuantityException {
        Item combinedItem = inventoryBook.addOnExistingItem(item);
        updateItemListFilter(PREDICATE_SHOW_ALL_ITEMS);
        return combinedItem;
    }

    @Override
    public void setItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        inventoryBook.setItem(target, editedItem);
    }

    //=========== Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedInventoryBook}
     */
    @Override
    public ObservableList<Item> getFilteredAndSortedItemList() {
        return new SortedList<>(filteredItems, ITEM_COMPARATOR);
    }

    @Override
    public void updateItemListFilter(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    //=========== Redo/Undo ===============================================================================

    /**
     * Copies the current {@code InventoryBook} in the state list.
     */
    @Override
    public void commit() {
        assert inventoryBookStatePointer < inventoryBookStateList.size();

        if (canRedo()) {
            inventoryBookStateList = inventoryBookStateList.subList(0, inventoryBookStatePointer + 1);
        }
        if (inventoryBookStateIsFull()) {
            inventoryBookStateList.remove(0);
            inventoryBookStatePointer--;
        }

        inventoryBookStateList.add(new InventoryBook(inventoryBook));
        inventoryBookStatePointer++;
    }

    /**
     * Shifts the current {@code InventoryBook} to the previous one in the state list.
     * @throws UndoRedoLimitReachedException if there is nothing left to undo
     */
    @Override
    public void undo() throws UndoRedoLimitReachedException {
        if (canUndo()) {
            inventoryBookStatePointer--;
            inventoryBook.resetData(inventoryBookStateList.get(inventoryBookStatePointer));
        } else {
            throw new UndoRedoLimitReachedException(MESSAGE_UNDO_LIMIT_REACHED);
        }
    }

    /**
     * Shifts the current {@code InventoryBook} to the next one in the state list.
     * @throws UndoRedoLimitReachedException if there is nothing left to redo
     */
    @Override
    public void redo() throws UndoRedoLimitReachedException {
        if (canRedo()) {
            inventoryBookStatePointer++;
            inventoryBook.resetData(inventoryBookStateList.get(inventoryBookStatePointer));
        } else {
            throw new UndoRedoLimitReachedException(MESSAGE_REDO_LIMIT_REACHED);
        }
    }

    @Override
    public void setStatesLimit(int limit) {
        statesLimit = limit;
    }

    private boolean canUndo() {
        return inventoryBookStatePointer > 0;
    }

    private boolean canRedo() {
        return inventoryBookStatePointer < inventoryBookStateList.size() - 1;
    }

    private boolean inventoryBookStateIsFull() {
        return inventoryBookStateList.size() >= statesLimit;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof InventoryModelManager)) {
            return false;
        }

        // state check
        InventoryModelManager other = (InventoryModelManager) obj;
        return inventoryBook.equals(other.inventoryBook)
                && userPrefs.equals(other.userPrefs)
                && filteredItems.equals(other.filteredItems);
    }

}
