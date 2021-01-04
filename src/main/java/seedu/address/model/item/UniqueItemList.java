package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.model.item.exceptions.ItemNotFoundException;
import seedu.address.model.item.exceptions.OverflowQuantityException;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * A item is considered unique by comparing using {@code Item#isSameItem(Item)}. As such, adding and updating of
 * items uses Item#isSameItem(Item) for equality so as to ensure that the item being added or updated is
 * unique in terms of identity in the UniqueItemList. However, the removal of a item uses Item#equals(Object) so
 * as to ensure that the item with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Item#isSameItem(Item)
 */
public class UniqueItemList implements Iterable<Item> {

    private final ObservableList<Item> internalList = FXCollections.observableArrayList();
    private final ObservableList<Item> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(Item toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameItem);
    }

    /**
     * Adds a item to the list.
     * The item must not already exist in the list.
     */
    public void add(Item toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the list.
     * The item identity of {@code editedItem} must not be the same as another existing item in the list.
     */
    public void setItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ItemNotFoundException();
        }

        if (!target.isSameItem(editedItem) && contains(editedItem)) {
            throw new DuplicateItemException();
        }

        internalList.set(index, editedItem);
    }

    /**
     * Combines quantity and tags of existing item with item provided
     * @param toAdd item provided to combine with existing item
     * @return combined item
     */
    public Item addOnExistingItem(Item toAdd) throws OverflowQuantityException {
        requireAllNonNull(toAdd);

        int index = -1;
        Item existingItem = null;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).isSameItem(toAdd)) {
                index = i;
                existingItem = internalList.get(i);
                break;
            }
        }

        if (index == -1 || existingItem == null) {
            throw new ItemNotFoundException();
        }

        Name name = toAdd.getName();
        Quantity quantity = toAdd.getQuantity().add(existingItem.getQuantity());
        Supplier supplier = toAdd.getSupplier();
        Set<Tag> providedItemTags = toAdd.getTags();
        Set<Tag> existingItemTags = existingItem.getTags();
        Set<Tag> combinedTags = new HashSet<>();
        combinedTags.addAll(providedItemTags);
        combinedTags.addAll(existingItemTags);

        // four cases for maxQuantity
        // 1) if existing item have maxQuantity, toAdd item does not
        // 2) if both existing item and toAdd does not have max quantity
        // 3) if both existing item and toAdd item have maxQuantity
        // 4) if existing item does not have maxQuantity, but toAdd does
        // case 3 and 4 are handled upstream @ ItemAddCommand via throwing MESSAGE_CHANGE_MAX_ON_EXISTING_ITEM
        // Only need to consider case 1 and 2
        assert toAdd.getMaxQuantity().isEmpty();
        Quantity maxQuantity = existingItem.getMaxQuantity().orElse(null);

        Metric metric = existingItem.getMetric().orElse(null);

        Item toReplace = new Item(name, quantity, supplier, combinedTags, maxQuantity, metric);
        internalList.set(index, toReplace);
        return toReplace;
    }

    /**
     * Removes the equivalent item from the list.
     * The item must exist in the list.
     */
    public void remove(Item toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ItemNotFoundException();
        }
    }

    public void setItems(UniqueItemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<Item> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throw new DuplicateItemException();
        }

        internalList.setAll(items);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Item> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Item> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                        && internalList.equals(((UniqueItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code items} contains only unique items.
     */
    private boolean itemsAreUnique(List<Item> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSameItem(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
