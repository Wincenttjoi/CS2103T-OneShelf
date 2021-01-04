package seedu.address.storage.delivery;

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
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;

/**
 * A class to access DeliveryBook data stored as a json file on the hard disk.
 */
public class JsonDeliveryBookStorage implements DeliveryBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDeliveryBookStorage.class);

    private Path filePath;

    public JsonDeliveryBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDeliveryBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDeliveryBook> readDeliveryBook() throws DataConversionException {
        return readDeliveryBook(filePath);
    }

    /**
     * Similar to {@link #readDeliveryBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDeliveryBook> readDeliveryBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDeliveryBook> jsonDeliveryBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableDeliveryBook.class);
        if (!jsonDeliveryBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDeliveryBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDeliveryBook(ReadOnlyDeliveryBook deliveryBook) throws IOException {
        saveDeliveryBook(deliveryBook, filePath);
    }

    /**
     * Similar to {@link #saveDeliveryBook(ReadOnlyDeliveryBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDeliveryBook(ReadOnlyDeliveryBook deliveryBook, Path filePath) throws IOException {
        requireNonNull(deliveryBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDeliveryBook(deliveryBook), filePath);
    }

}



