package seedu.address.model.inventorymodel;

import javafx.collections.ObservableList;
import seedu.address.model.item.Item;

/**
 * Unmodifiable view of an inventory book
 */
public interface ReadOnlyInventoryBook {

    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemList();

}
