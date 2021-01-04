package seedu.address.storage.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.model.item.Item;

/**
 * An Immutable InventoryBook that is serializable to JSON format.
 */
@JsonRootName(value = "inventorybook")
class JsonSerializableInventoryBook {

    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate item(s).";

    private final List<JsonAdaptedItem> items = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInventoryBook} with the given items.
     */
    @JsonCreator
    public JsonSerializableInventoryBook(@JsonProperty("items") List<JsonAdaptedItem> items) {
        this.items.addAll(items);
    }

    /**
     * Converts a given {@code ReadOnlyInventoryBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInventoryBook}.
     */
    public JsonSerializableInventoryBook(ReadOnlyInventoryBook source) {
        items.addAll(source.getItemList().stream().map(JsonAdaptedItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this inventory book into the model's {@code InventoryBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public InventoryBook toModelType() throws IllegalValueException {
        InventoryBook inventoryBook = new InventoryBook();
        for (JsonAdaptedItem jsonAdaptedItem : items) {
            Item item = jsonAdaptedItem.toModelType();
            if (inventoryBook.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            inventoryBook.addItem(item);
        }
        return inventoryBook;
    }

}
