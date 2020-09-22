package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.CHICKEN;
import static seedu.address.testutil.TypicalItems.DUCK;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.item.NameContainsKeywordsPredicate;
import seedu.address.testutil.InventoryBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new InventoryBook(), new InventoryBook(modelManager.getInventoryBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setInventoryBookFilePath(Paths.get("inventory/book/file/path"));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setInventoryBookFilePath(Paths.get("new/inventory/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setInventoryBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setInventoryBookFilePath(null));
    }

    @Test
    public void setInventoryBookFilePath_validPath_setsInventoryBookFilePath() {
        Path path = Paths.get("inventory/book/file/path");
        modelManager.setInventoryBookFilePath(path);
        assertEquals(path, modelManager.getInventoryBookFilePath());
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInInventoryBook_returnsFalse() {
        assertFalse(modelManager.hasItem(CHICKEN));
    }

    @Test
    public void hasItem_itemInInventoryBook_returnsTrue() {
        modelManager.addItem(CHICKEN);
        assertTrue(modelManager.hasItem(CHICKEN));
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredItemList().remove(0));
    }

    @Test
    public void equals() {
        InventoryBook inventoryBook = new InventoryBookBuilder().withItem(CHICKEN).withItem(DUCK).build();
        InventoryBook differentInventoryBook = new InventoryBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(inventoryBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(inventoryBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different inventoryBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentInventoryBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = CHICKEN.getName().fullName.split("\\s+");
        modelManager.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(inventoryBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInventoryBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(inventoryBook, differentUserPrefs)));
    }
}
