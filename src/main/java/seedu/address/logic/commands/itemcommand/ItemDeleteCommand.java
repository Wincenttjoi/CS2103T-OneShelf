package seedu.address.logic.commands.itemcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.item.Item;

/**
 * Deletes a item identified using it's displayed index from the inventory book.
 */
public class ItemDeleteCommand extends ItemCommand {

    public static final String COMMAND_WORD = "delete-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Item: \n%1$s";

    private final Index targetIndex;

    public ItemDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Models models) throws CommandException {
        requireNonNull(models);
        requireNonNull(models.getInventoryModel());
        InventoryModel inventoryModel = models.getInventoryModel();
        List<Item> lastShownList = inventoryModel.getFilteredAndSortedItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToDelete = lastShownList.get(targetIndex.getZeroBased());
        inventoryModel.deleteItem(itemToDelete);
        models.commit();
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((ItemDeleteCommand) other).targetIndex)); // state check
    }
}
