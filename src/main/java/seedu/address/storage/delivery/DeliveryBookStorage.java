package seedu.address.storage.delivery;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;

/**
 * Represents a storage for {@link DeliveryBook}.
 */
public interface DeliveryBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDeliveryBookFilePath();

    /**
     * Returns DeliveryBook data as a {@link ReadOnlyDeliveryBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDeliveryBook> readDeliveryBook() throws DataConversionException, IOException;

    /**
     * @see #getDeliveryBookFilePath()
     */
    Optional<ReadOnlyDeliveryBook> readDeliveryBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDeliveryBook} to the storage.
     * @param inventoryBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDeliveryBook(ReadOnlyDeliveryBook inventoryBook) throws IOException;

    /**
     * @see #saveDeliveryBook(ReadOnlyDeliveryBook)
     */
    void saveDeliveryBook(ReadOnlyDeliveryBook inventoryBook, Path filePath) throws IOException;

}
