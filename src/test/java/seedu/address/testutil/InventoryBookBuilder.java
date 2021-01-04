package seedu.address.testutil;

import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.item.Item;

/**
 * A utility class to help with building Inventorybook objects.
 *
 */
public class InventoryBookBuilder {

    private InventoryBook inventoryBook;

    public InventoryBookBuilder() {
        inventoryBook = new InventoryBook();
    }

    public InventoryBookBuilder(InventoryBook inventoryBook) {
        this.inventoryBook = inventoryBook;
    }

    /**
     * Adds a new {@code Item} to the {@code InventoryBook} that we are building.
     */
    public InventoryBookBuilder withItem(Item item) {
        inventoryBook.addItem(item);
        return this;
    }

    public InventoryBook build() {
        return inventoryBook;
    }
}
