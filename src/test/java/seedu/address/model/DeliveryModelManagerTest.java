package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.deliverymodel.DeliveryModel.PREDICATE_SHOW_ALL_DELIVERIES;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.DeliveryModelManager;

public class DeliveryModelManagerTest {

    private DeliveryModelManager modelManager = new DeliveryModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new DeliveryBook(), new DeliveryBook(modelManager.getDeliveryBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDeliveryBookFilePath(Paths.get("delivery/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDeliveryBookFilePath(Paths.get("new/delivery/book/file/path"));
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
    public void setDeliveryBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDeliveryBookFilePath(null));
    }

    @Test
    public void setDeliveryBookFilePath_validPath_setsDeliveryBookFilePath() {
        Path path = Paths.get("inventory/book/file/path");
        modelManager.setDeliveryBookFilePath(path);
        assertEquals(path, modelManager.getDeliveryBookFilePath());
    }

    @Test
    public void getFilteredDeliveryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () -> modelManager.getFilteredAndSortedDeliveryList().remove(0));
    }

    @Test
    public void equals() {
        DeliveryBook deliveryBook = new DeliveryBook();
        DeliveryBook differentDeliveryBook = new DeliveryBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new DeliveryModelManager(deliveryBook, userPrefs);
        DeliveryModelManager modelManagerCopy = new DeliveryModelManager(deliveryBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDeliveryBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new DeliveryModelManager(deliveryBook, differentUserPrefs)));
    }
}
