package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.model.item.Item;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the InventoryBook.
     *
     * @see InventoryModel#getInventoryBook()
     */
    ReadOnlyInventoryBook getInventoryBook();

    /** Returns an unmodifiable view of the filtered list of items */
    ObservableList<Item> getFilteredAndSortedItemList();

    /**
     * Returns the user prefs' inventory book file path.
     */
    Path getInventoryBookFilePath();

    /**
     * Returns the DeliveryBook
     * @see DeliveryModel#getDeliveryBook()
     */
    ReadOnlyDeliveryBook getDeliveryBook();

    /**
     * returns an unmodifiable view of the filtered list of deliveries
     */
    ObservableList<Delivery> getFilteredAndSortedDeliveryList();

    /**
     * Returns the user prefs' delivery book file path.
     * @return
     */
    Path getDeliveryBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
