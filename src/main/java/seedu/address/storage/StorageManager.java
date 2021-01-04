package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.storage.delivery.DeliveryBookStorage;
import seedu.address.storage.item.InventoryBookStorage;

/**
 * Manages storage of InventoryBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private InventoryBookStorage inventoryBookStorage;
    private DeliveryBookStorage deliveryBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code InventoryBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(InventoryBookStorage inventoryBookStorage,
                          DeliveryBookStorage deliveryBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.inventoryBookStorage = inventoryBookStorage;
        this.deliveryBookStorage = deliveryBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ InventoryBook methods ==============================

    @Override
    public Path getInventoryBookFilePath() {
        return inventoryBookStorage.getInventoryBookFilePath();
    }

    @Override
    public Optional<ReadOnlyInventoryBook> readInventoryBook() throws DataConversionException, IOException {
        return readInventoryBook(inventoryBookStorage.getInventoryBookFilePath());
    }

    @Override
    public Optional<ReadOnlyInventoryBook> readInventoryBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return inventoryBookStorage.readInventoryBook(filePath);
    }

    @Override
    public void saveInventoryBook(ReadOnlyInventoryBook inventoryBook) throws IOException {
        saveInventoryBook(inventoryBook, inventoryBookStorage.getInventoryBookFilePath());
    }

    @Override
    public void saveInventoryBook(ReadOnlyInventoryBook inventoryBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        inventoryBookStorage.saveInventoryBook(inventoryBook, filePath);
    }

    // ================ DeliveryBook methods ==============================

    @Override
    public Path getDeliveryBookFilePath() {
        return deliveryBookStorage.getDeliveryBookFilePath();
    }

    // TODO
    @Override
    public Optional<ReadOnlyDeliveryBook> readDeliveryBook() throws DataConversionException, IOException {
        return readDeliveryBook(deliveryBookStorage.getDeliveryBookFilePath());
    }

    @Override
    public Optional<ReadOnlyDeliveryBook> readDeliveryBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return deliveryBookStorage.readDeliveryBook(filePath);
    }

    @Override
    public void saveDeliveryBook(ReadOnlyDeliveryBook deliveryBook) throws IOException {
        saveDeliveryBook(deliveryBook, deliveryBookStorage.getDeliveryBookFilePath());
    }

    @Override
    public void saveDeliveryBook(ReadOnlyDeliveryBook deliveryBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        deliveryBookStorage.saveDeliveryBook(deliveryBook, filePath);
    }

}
