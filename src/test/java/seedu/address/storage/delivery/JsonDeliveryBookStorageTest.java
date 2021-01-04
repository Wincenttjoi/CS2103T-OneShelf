package seedu.address.storage.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.AARON_MANUAL;
import static seedu.address.testutil.TypicalDeliveries.DAMITH_MANUAL;
import static seedu.address.testutil.TypicalDeliveries.MARCUS;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;

public class JsonDeliveryBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDeliveryBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDeliveryBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readDeliveryBook(null));
    }

    private java.util.Optional<ReadOnlyDeliveryBook> readDeliveryBook(String filePath) throws Exception {
        return new JsonDeliveryBookStorage(Paths.get(filePath))
                .readDeliveryBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDeliveryBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readDeliveryBook("notJsonFormatDeliveryBook.json"));
    }

    @Test
    public void readDeliveryBook_invalidDeliveryDeliveryBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDeliveryBook("invalidDeliveryDeliveryBook.json"));
    }

    @Test
    public void readDeliveryBook_invalidAndValidDeliveryDeliveryBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDeliveryBook("invalidAndValidDeliveryDeliveryBook.json"));
    }

    @Test
    public void readAndSaveDeliveryBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDeliveryBook.json");
        DeliveryBook original = getTypicalDeliveryBook();
        JsonDeliveryBookStorage jsonDeliveryBookStorage = new JsonDeliveryBookStorage(filePath);

        // Save in new file and read back
        jsonDeliveryBookStorage.saveDeliveryBook(original, filePath);
        ReadOnlyDeliveryBook readBack = jsonDeliveryBookStorage.readDeliveryBook(filePath).get();
        assertEquals(original, new DeliveryBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDelivery(DAMITH_MANUAL);
        original.removeDelivery(MARCUS);
        jsonDeliveryBookStorage.saveDeliveryBook(original, filePath);
        readBack = jsonDeliveryBookStorage.readDeliveryBook(filePath).get();
        assertEquals(original, new DeliveryBook(readBack));

        // Save and read without specifying file path
        original.addDelivery(AARON_MANUAL);
        jsonDeliveryBookStorage.saveDeliveryBook(original); // file path not specified
        readBack = jsonDeliveryBookStorage.readDeliveryBook().get(); // file path not specified
        assertEquals(original, new DeliveryBook(readBack));

    }

    @Test
    public void saveDeliveryBook_nullDeliveryBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDeliveryBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code deliveryBook} at the specified {@code filePath}.
     */
    private void saveDeliveryBook(ReadOnlyDeliveryBook deliveryBook, String filePath) {
        try {
            new JsonDeliveryBookStorage(Paths.get(filePath))
                    .saveDeliveryBook(deliveryBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDeliveryBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDeliveryBook(new DeliveryBook(), null));
    }
}
