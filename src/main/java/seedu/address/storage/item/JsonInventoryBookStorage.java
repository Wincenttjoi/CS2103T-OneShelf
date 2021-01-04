package seedu.address.storage.item;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;

/**
 * A class to access InventoryBook data stored as a json file on the hard disk.
 */
public class JsonInventoryBookStorage implements InventoryBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInventoryBookStorage.class);

    private Path filePath;

    public JsonInventoryBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInventoryBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInventoryBook> readInventoryBook() throws DataConversionException {
        return readInventoryBook(filePath);
    }

    /**
     * Similar to {@link #readInventoryBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyInventoryBook> readInventoryBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableInventoryBook> jsonInventoryBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableInventoryBook.class);
        if (!jsonInventoryBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInventoryBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInventoryBook(ReadOnlyInventoryBook inventoryBook) throws IOException {
        saveInventoryBook(inventoryBook, filePath);
    }

    /**
     * Similar to {@link #saveInventoryBook(ReadOnlyInventoryBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInventoryBook(ReadOnlyInventoryBook inventoryBook, Path filePath) throws IOException {
        requireNonNull(inventoryBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInventoryBook(inventoryBook), filePath);
    }

}
