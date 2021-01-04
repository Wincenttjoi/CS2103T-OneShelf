package seedu.address.logic.commands.itemcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.item.Item;

/**
 * Finds and lists all items in inventory book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ItemFindCommand extends ItemCommand {

    public static final String COMMAND_WORD = "find-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME | "
            + PREFIX_SUPPLIER + "SUPPLIER | "
            + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " n/chicken t/meat";

    private final Predicate<Item> predicate;

    public ItemFindCommand(Predicate<Item> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Models models) {
        requireNonNull(models);
        requireNonNull(models.getInventoryModel());

        InventoryModel inventoryModel = models.getInventoryModel();
        inventoryModel.updateItemListFilter(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW,
                        inventoryModel.getFilteredAndSortedItemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemFindCommand // instanceof handles nulls
                && predicate.equals(((ItemFindCommand) other).predicate)); // state check
    }
}
