package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUPPLIER_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUPPLIER_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DUCK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.CHICKEN;
import static seedu.address.testutil.TypicalItems.DUCK_WITH_MAX_QUANTITY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.model.item.exceptions.ItemNotFoundException;
import seedu.address.model.item.exceptions.OverflowQuantityException;
import seedu.address.testutil.ItemBuilder;

public class UniqueItemListTest {

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.contains(null));
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(CHICKEN));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(CHICKEN);
        assertTrue(uniqueItemList.contains(CHICKEN));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(CHICKEN);
        Item editedChicken = new ItemBuilder(CHICKEN)
            .withSupplier(VALID_SUPPLIER_CHICKEN)
            .withTags(VALID_TAG_CHICKEN)
            .build();
        assertTrue(uniqueItemList.contains(editedChicken));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.add(null));
    }

    @Test
    public void add_duplicateItem_throwsDuplicateItemException() {
        uniqueItemList.add(CHICKEN);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.add(CHICKEN));
    }

    @Test
    public void setItem_nullTargetItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(null, CHICKEN));
    }

    @Test
    public void setItem_nullEditedItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItem(CHICKEN, null));
    }

    @Test
    public void setItem_targetItemNotInList_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.setItem(CHICKEN, CHICKEN));
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueItemList.add(CHICKEN);
        uniqueItemList.setItem(CHICKEN, CHICKEN);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(CHICKEN);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueItemList.add(CHICKEN);
        Item editedChicken = new ItemBuilder(CHICKEN).withSupplier(VALID_SUPPLIER_DUCK).withTags(VALID_TAG_DUCK)
                .build();
        uniqueItemList.setItem(CHICKEN, editedChicken);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(editedChicken);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueItemList.add(CHICKEN);
        uniqueItemList.setItem(CHICKEN, DUCK_WITH_MAX_QUANTITY);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(DUCK_WITH_MAX_QUANTITY);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueItemList.add(CHICKEN);
        uniqueItemList.add(DUCK_WITH_MAX_QUANTITY);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItem(CHICKEN, DUCK_WITH_MAX_QUANTITY));
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.remove(null));
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.remove(CHICKEN));
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueItemList.add(CHICKEN);
        uniqueItemList.remove(CHICKEN);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((UniqueItemList) null));
    }

    @Test
    public void setItems_uniqueItemList_replacesOwnListWithProvidedUniqueItemList() {
        uniqueItemList.add(CHICKEN);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(DUCK_WITH_MAX_QUANTITY);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueItemList.setItems((List<Item>) null));
    }

    @Test
    public void setItems_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(CHICKEN);
        List<Item> itemList = Collections.singletonList(DUCK_WITH_MAX_QUANTITY);
        uniqueItemList.setItems(itemList);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(DUCK_WITH_MAX_QUANTITY);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_listWithDuplicateItems_throwsDuplicateItemException() {
        List<Item> listWithDuplicateItems = Arrays.asList(CHICKEN, CHICKEN);
        assertThrows(DuplicateItemException.class, () -> uniqueItemList.setItems(listWithDuplicateItems));
    }

    @Test
    public void addOnExistingItem_itemDoesNotExist_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> uniqueItemList.addOnExistingItem(CHICKEN));
    }

    @Test
    public void addOnExistingItem_existingItem_success() throws OverflowQuantityException {
        uniqueItemList.add(CHICKEN);
        uniqueItemList.addOnExistingItem(CHICKEN);
        Item item = new ItemBuilder().withQuantity("24").withSupplier("GIANT").withTags("meat").build();
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(item);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueItemList.asUnmodifiableObservableList().remove(0));
    }
}

