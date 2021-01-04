package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.storage.delivery.DeliveryBookStorage;
import seedu.address.storage.item.InventoryBookStorage;

/**
 * API of the Storage component
 */
public interface Storage extends InventoryBookStorage, UserPrefsStorage, DeliveryBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getInventoryBookFilePath();

    @Override
    Optional<ReadOnlyInventoryBook> readInventoryBook() throws DataConversionException, IOException;

    @Override
    void saveInventoryBook(ReadOnlyInventoryBook inventoryBook) throws IOException;

    @Override
    void saveDeliveryBook(ReadOnlyDeliveryBook deliveryBook) throws IOException;

    @Override
    Optional<ReadOnlyDeliveryBook> readDeliveryBook() throws DataConversionException, IOException;

}
