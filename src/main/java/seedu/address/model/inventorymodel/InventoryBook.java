package seedu.address.model.inventorymodel;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.item.Item;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.item.exceptions.OverflowQuantityException;

/**
 * Wraps all data at the inventory-book level
 * Duplicates are not allowed (by .isSameItem comparison)
 */
public class InventoryBook implements ReadOnlyInventoryBook {

    private final UniqueItemList items;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        items = new UniqueItemList();
    }

    public InventoryBook() {}

    /**
     * Creates an InventoryBook using the Items in the {@code toBeCopied}
     */
    public InventoryBook(ReadOnlyInventoryBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }

    /**
     * Resets the existing data of this {@code InventoryBook} with {@code newData}.
     */
    public void resetData(ReadOnlyInventoryBook newData) {
        requireNonNull(newData);
        setItems(newData.getItemList());
    }

    //// item-level operations

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory book.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds a item to the inventory book.
     * The item must not already exist in the inventory book.
     */
    public void addItem(Item p) {
        items.add(p);
    }

    /**
     * Combines quantity and tags of existing item with item provided
     * @param item item provided to combine with existing item
     * @return combined item
     */
    public Item addOnExistingItem(Item item) throws OverflowQuantityException {
        return items.addOnExistingItem(item);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the inventory book.
     * The item identity of {@code editedItem} must not be the same as another existing item in the inventory book.
     */
    public void setItem(Item target, Item editedItem) {
        requireNonNull(editedItem);

        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code InventoryBook}.
     * {@code key} must exist in the inventory book.
     */
    public void removeItem(Item key) {
        items.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return items.asUnmodifiableObservableList().size() + " items";
        // TODO: refine later
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InventoryBook // instanceof handles nulls
                && items.equals(((InventoryBook) other).items));
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
