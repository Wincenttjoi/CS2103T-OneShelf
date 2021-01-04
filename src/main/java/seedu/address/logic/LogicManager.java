package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.parser.OneShelfBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Models;
import seedu.address.model.ModelsManager;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.model.item.Item;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Models models;
    private final InventoryModel inventoryModel;
    private final DeliveryModel deliveryModel;
    private final Storage storage;
    private final OneShelfBookParser oneShelfBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(InventoryModel inventoryModel, DeliveryModel deliveryModel, Storage storage) {
        assert inventoryModel != null && deliveryModel != null;

        this.inventoryModel = inventoryModel;
        this.deliveryModel = deliveryModel;
        models = new ModelsManager(inventoryModel, deliveryModel);
        this.storage = storage;
        oneShelfBookParser = new OneShelfBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = oneShelfBookParser.parseCommand(commandText);
        commandResult = command.execute(models);

        try {
            storage.saveInventoryBook(inventoryModel.getInventoryBook());
            storage.saveDeliveryBook(deliveryModel.getDeliveryBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyInventoryBook getInventoryBook() {
        return inventoryModel.getInventoryBook();
    }

    @Override
    public ObservableList<Item> getFilteredAndSortedItemList() {
        return inventoryModel.getFilteredAndSortedItemList();
    }

    @Override
    public Path getInventoryBookFilePath() {
        return inventoryModel.getInventoryBookFilePath();
    }

    @Override
    public ReadOnlyDeliveryBook getDeliveryBook() {
        return deliveryModel.getDeliveryBook();
    }

    @Override
    public ObservableList<Delivery> getFilteredAndSortedDeliveryList() {
        return deliveryModel.getFilteredAndSortedDeliveryList();
    }

    @Override
    public Path getDeliveryBookFilePath() {
        return deliveryModel.getDeliveryBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return inventoryModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        inventoryModel.setGuiSettings(guiSettings);
    }
}
