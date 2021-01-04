package seedu.address.storage.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.CHICKEN;
import static seedu.address.testutil.TypicalItems.LAMB;
import static seedu.address.testutil.TypicalItems.TUNA;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;

public class JsonInventoryBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonInventoryBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readInventoryBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readInventoryBook(null));
    }

    private java.util.Optional<ReadOnlyInventoryBook> readInventoryBook(String filePath) throws Exception {
        return new JsonInventoryBookStorage(Paths.get(filePath))
                .readInventoryBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInventoryBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readInventoryBook("notJsonFormatInventoryBook.json"));
    }

    @Test
    public void readInventoryBook_invalidItemInventoryBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readInventoryBook("invalidItemInventoryBook.json"));
    }

    @Test
    public void readInventoryBook_invalidAndValidItemInventoryBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readInventoryBook("invalidAndValidItemInventoryBook.json"));
    }

    @Test
    public void readAndSaveInventoryBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempInventoryBook.json");
        InventoryBook original = getTypicalInventoryBook();
        JsonInventoryBookStorage jsonInventoryBookStorage = new JsonInventoryBookStorage(filePath);

        // Save in new file and read back
        jsonInventoryBookStorage.saveInventoryBook(original, filePath);
        ReadOnlyInventoryBook readBack = jsonInventoryBookStorage.readInventoryBook(filePath).get();
        assertEquals(original, new InventoryBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addItem(TUNA);
        original.removeItem(CHICKEN);
        jsonInventoryBookStorage.saveInventoryBook(original, filePath);
        readBack = jsonInventoryBookStorage.readInventoryBook(filePath).get();
        assertEquals(original, new InventoryBook(readBack));

        // Save and read without specifying file path
        original.addItem(LAMB);
        jsonInventoryBookStorage.saveInventoryBook(original); // file path not specified
        readBack = jsonInventoryBookStorage.readInventoryBook().get(); // file path not specified
        assertEquals(original, new InventoryBook(readBack));

    }

    @Test
    public void saveInventoryBook_nullInventoryBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInventoryBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code inventoryBook} at the specified {@code filePath}.
     */
    private void saveInventoryBook(ReadOnlyInventoryBook inventoryBook, String filePath) {
        try {
            new JsonInventoryBookStorage(Paths.get(filePath))
                    .saveInventoryBook(inventoryBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInventoryBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInventoryBook(new InventoryBook(), null));
    }
}
