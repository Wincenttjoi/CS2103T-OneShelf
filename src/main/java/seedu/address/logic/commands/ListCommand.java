package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.inventorymodel.InventoryModel.PREDICATE_SHOW_ALL_ITEMS;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.inventorymodel.InventoryModel;

/**
 * Lists all items in the inventory book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all items";


    @Override
    public CommandResult execute(InventoryModel inventoryModel) {
        requireNonNull(inventoryModel);
        inventoryModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
