package seedu.address.logic.commands.itemcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.InventoryModel;

/**
 * Clears the inventory book.
 */
public class ItemClearCommand extends ItemCommand {

    public static final String COMMAND_WORD = "clear-i";
    public static final String MESSAGE_SUCCESS = "Inventory book has been cleared!";


    @Override
    public CommandResult execute(Models models) {
        requireNonNull(models);
        requireNonNull(models.getInventoryModel());
        InventoryModel inventoryModel = models.getInventoryModel();

        inventoryModel.setInventoryBook(new InventoryBook());
        models.commit();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
