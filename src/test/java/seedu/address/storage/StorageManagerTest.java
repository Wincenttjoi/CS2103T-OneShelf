package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.storage.delivery.JsonDeliveryBookStorage;
import seedu.address.storage.item.JsonInventoryBookStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonInventoryBookStorage inventoryBookStorage = new JsonInventoryBookStorage(getTempFilePath("ab"));
        JsonDeliveryBookStorage deliveryBookStorage = new JsonDeliveryBookStorage(getTempFilePath("db"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(inventoryBookStorage, deliveryBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void inventoryBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInventoryBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in
         * {@link JsonInventoryBookStorageTest} class.
         */
        InventoryBook original = getTypicalInventoryBook();
        storageManager.saveInventoryBook(original);
        ReadOnlyInventoryBook retrieved = storageManager.readInventoryBook().get();
        assertEquals(original, new InventoryBook(retrieved));
    }

    @Test
    public void getInventoryBookFilePath() {
        assertNotNull(storageManager.getInventoryBookFilePath());
    }

}
