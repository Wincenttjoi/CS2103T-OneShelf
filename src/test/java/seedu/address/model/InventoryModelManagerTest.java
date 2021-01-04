package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.inventorymodel.InventoryModel.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.CHICKEN;
import static seedu.address.testutil.TypicalItems.DUCK_WITH_MAX_QUANTITY;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.item.predicate.NameContainsKeywordsPredicate;
import seedu.address.testutil.InventoryBookBuilder;

public class InventoryModelManagerTest {

    private InventoryModelManager modelManager = new InventoryModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
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
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setInventoryBookFilePath(Paths.get("new/inventory/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
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
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredAndSortedItemList().remove(0));
    }

    @Test
    public void equals() {
        InventoryBook inventoryBook = new InventoryBookBuilder()
                .withItem(CHICKEN).withItem(DUCK_WITH_MAX_QUANTITY).build();
        InventoryBook differentInventoryBook = new InventoryBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new InventoryModelManager(inventoryBook, userPrefs);
        InventoryModelManager modelManagerCopy = new InventoryModelManager(inventoryBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different inventoryBook -> returns false
        assertFalse(modelManager.equals(new InventoryModelManager(differentInventoryBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = CHICKEN.getName().fullName.split("\\s+");
        modelManager.updateItemListFilter(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new InventoryModelManager(inventoryBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateItemListFilter(PREDICATE_SHOW_ALL_ITEMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInventoryBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new InventoryModelManager(inventoryBook, differentUserPrefs)));
    }
}
