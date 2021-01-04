package seedu.address.model.inventorymodel;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.comparator.ItemNameComparator;
import seedu.address.model.item.comparator.ItemQuantityPercentageComparator;
import seedu.address.model.item.exceptions.OverflowQuantityException;

/**
 * The API of the InventoryModel component.
 */
public interface InventoryModel extends Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /** {@code Comparator} that returns a ItemComparator */
    Comparator<Item> ITEM_COMPARATOR = new ItemQuantityPercentageComparator().thenComparing(new ItemNameComparator());

    /**
     * Returns the user prefs' inventory book file path.
     */
    Path getInventoryBookFilePath();

    /**
     * Sets the user prefs' inventory book file path.
     */
    void setInventoryBookFilePath(Path inventoryBookFilePath);

    /**
     * Replaces inventory book data with the data in {@code inventoryBook}.
     */
    void setInventoryBook(ReadOnlyInventoryBook inventoryBook);

    /** Returns the InventoryBook */
    ReadOnlyInventoryBook getInventoryBook();

    //================================ METHODS STARTS HERE ================================

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory book.
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given item.
     * The item must exist in the inventory book.
     */
    void deleteItem(Item target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the inventory book.
     */
    void addItem(Item item);

    /**
     * Combines quantity and tags of existing item with item provided
     * @param item item provided to combine with existing item
     * @return combined item
     */
    Item addOnExistingItem(Item item) throws OverflowQuantityException;

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in the inventory book.
     * The item identity of {@code editedItem} must not be the same as another existing item in the inventory book.
     */
    void setItem(Item target, Item editedItem);

    /** Returns an unmodifiable view of the filtered and sorted item list */
    ObservableList<Item> getFilteredAndSortedItemList();

    /**
     * Updates the filter of the filtered and sorted item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateItemListFilter(Predicate<Item> predicate);
}
