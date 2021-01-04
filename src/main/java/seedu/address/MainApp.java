package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.delivery.DeliveryBookStorage;
import seedu.address.storage.delivery.JsonDeliveryBookStorage;
import seedu.address.storage.item.InventoryBookStorage;
import seedu.address.storage.item.JsonInventoryBookStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected InventoryModel inventoryModel;
    protected DeliveryModel deliveryModel;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing OneShelf ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        InventoryBookStorage inventoryBookStorage = new JsonInventoryBookStorage(userPrefs.getInventoryBookFilePath());
        DeliveryBookStorage deliveryBookStorage = new JsonDeliveryBookStorage(userPrefs.getDeliveryBookFilePath());
        storage = new StorageManager(inventoryBookStorage, deliveryBookStorage, userPrefsStorage);

        initLogging(config);

        inventoryModel = initInventoryModelManager(storage, userPrefs);
        deliveryModel = initDeliveryModelManager(storage, userPrefs);

        logic = new LogicManager(inventoryModel, deliveryModel, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code InventoryModelManager}
     * with the data from {@code storage}'s inventory book and {@code userPrefs}. <br>
     * The data from the sample inventory book will be used instead if {@code storage}'s inventory book is not found,
     * or an empty inventory book will be used instead if errors occur when reading {@code storage}'s inventory book.
     */
    private InventoryModel initInventoryModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyInventoryBook> inventoryBookOptional;
        ReadOnlyInventoryBook initialData;
        try {
            inventoryBookOptional = storage.readInventoryBook();
            if (!inventoryBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample InventoryBook");
            }
            initialData = inventoryBookOptional.orElseGet(SampleDataUtil::getSampleInventoryBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty InventoryBook");
            initialData = new InventoryBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty InventoryBook");
            initialData = new InventoryBook();
        }
        logger.info("Initializing Inventory Book...");
        return new InventoryModelManager(initialData, userPrefs);
    }

    /**
     * Returns a {@code DeliveryModel} with the data from {@code storage}'s delivery book and {@code userPrefs}. <br>
     * The data from the sample delivery book will be used instead if {@code storage}'s delivery book is not found,
     * or an empty delivery book will be used instead if errors occur when reading {@code storage}'s delivery book.
     */
    private DeliveryModel initDeliveryModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyDeliveryBook> deliveryBookOptional;
        ReadOnlyDeliveryBook initialData;
        try {
            deliveryBookOptional = storage.readDeliveryBook();
            if (!deliveryBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample DeliveryBook");
            }
            initialData = deliveryBookOptional.orElseGet(SampleDataUtil::getSampleDeliveryBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DeliveryBook");
            initialData = new DeliveryBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DeliveryBook");
            initialData = new DeliveryBook();
        }
        logger.info("Initializing Delivery Book...");
        return new DeliveryModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty InventoryBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting OneShelf " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping OneShelf ] =============================");
        try {
            storage.saveUserPrefs(inventoryModel.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
