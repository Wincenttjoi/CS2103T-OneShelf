package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUPPLIER_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.CHICKEN;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.model.item.Item;
import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.testutil.ItemBuilder;

public class SupplierBookTest {

    private final InventoryBook inventoryBook = new InventoryBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventoryBook.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventoryBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyInventoryBook_replacesData() {
        InventoryBook newData = getTypicalInventoryBook();
        inventoryBook.resetData(newData);
        assertEquals(newData, inventoryBook);
    }

    @Test
    public void resetData_withDuplicateItem_throwsItemPersonException() {
        // Two persons with the same identity fields
        Item editedChicken = new ItemBuilder(CHICKEN)
                .withSupplier(VALID_SUPPLIER_CHICKEN)
                .withTags(VALID_TAG_CHICKEN)
                .build();
        List<Item> newItems = Arrays.asList(CHICKEN, editedChicken);
        InventoryBookStub newData = new InventoryBookStub(newItems);

        assertThrows(DuplicateItemException.class, () -> inventoryBook.resetData(newData));
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventoryBook.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInInventoryBook_returnsFalse() {
        assertFalse(inventoryBook.hasItem(CHICKEN));
    }

    @Test
    public void hasItem_itemInInventoryBook_returnsTrue() {
        inventoryBook.addItem(CHICKEN);
        assertTrue(inventoryBook.hasItem(CHICKEN));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInInventoryBook_returnsTrue() {
        inventoryBook.addItem(CHICKEN);
        Item editedChicken = new ItemBuilder(CHICKEN)
            .withSupplier(VALID_SUPPLIER_CHICKEN).withTags(VALID_TAG_CHICKEN)
            .build();
        assertTrue(inventoryBook.hasItem(editedChicken));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> inventoryBook.getItemList().remove(0));
    }

    /**
     * A stub ReadOnlyInventoryBook whose items list can violate interface constraints.
     */
    private static class InventoryBookStub implements ReadOnlyInventoryBook {
        private final ObservableList<Item> items = FXCollections.observableArrayList();

        InventoryBookStub(Collection<Item> items) {
            this.items.setAll(items);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }
    }

}
