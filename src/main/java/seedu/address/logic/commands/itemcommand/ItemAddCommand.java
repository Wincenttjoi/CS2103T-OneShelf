package seedu.address.logic.commands.itemcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_METRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.item.Item;
import seedu.address.model.item.exceptions.OverflowQuantityException;

/**
 * Adds a item to the inventory book.
 */
public class ItemAddCommand extends ItemCommand {

    public static final String COMMAND_WORD = "add-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a item to the inventory book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + "[" + PREFIX_SUPPLIER + "SUPPLIER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Chicken "
            + PREFIX_QUANTITY + "12 "
            + PREFIX_SUPPLIER + "NTUC "
            + PREFIX_TAG + "meat "
            + PREFIX_MAX_QUANTITY + "50 "
            + PREFIX_METRIC + "kg ";

    public static final String MESSAGE_SUCCESS = "New item added: \n%1$s";
    public static final String MESSAGE_ITEM_ADDED_TO_INVENTORY = "Item added to inventory. Stock is now: \n%1$s";

    public static final String MESSAGE_CHANGE_MAX_ON_EXISTING_ITEM =
            "You cannot change the max quantity when adding to existing items.";
    public static final String MESSAGE_CHANGE_METRIC_ON_EXISTING_ITEM =
            "You cannot change the metric when adding to existing items.";
    public static final String MESSAGE_QUANTITY_OVERFLOW = "Quantity must be within 9 digits!\n"
            + "Adding on to the existing item will exceed quantity's 9 digit limit!";

    private final Item toAdd;

    /**
     * Creates an ItemAddCommand to add the specified {@code Item}
     */
    public ItemAddCommand(Item item) {
        requireNonNull(item);
        toAdd = item;
    }

    @Override
    public CommandResult execute(Models models) throws CommandException {
        requireNonNull(models);
        requireNonNull(models.getInventoryModel());
        InventoryModel inventoryModel = models.getInventoryModel();

        CommandResult commandResult;

        if (inventoryModel.hasItem(toAdd)) {
            if (toAdd.getMaxQuantity().isPresent()) {
                throw new CommandException(MESSAGE_CHANGE_MAX_ON_EXISTING_ITEM);
            }
            if (toAdd.getMetric().isPresent()) {
                throw new CommandException(MESSAGE_CHANGE_METRIC_ON_EXISTING_ITEM);
            }
        }

        if (inventoryModel.hasItem(toAdd)) {
            try {
                Item toReplace = inventoryModel.addOnExistingItem(toAdd);
                commandResult = new CommandResult(String.format(MESSAGE_ITEM_ADDED_TO_INVENTORY, toReplace));
            } catch (OverflowQuantityException e) {
                throw new CommandException(MESSAGE_QUANTITY_OVERFLOW);
            }
        } else {
            inventoryModel.addItem(toAdd);
            commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        }

        models.commit();

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemAddCommand // instanceof handles nulls
                && toAdd.equals(((ItemAddCommand) other).toAdd));
    }
}
