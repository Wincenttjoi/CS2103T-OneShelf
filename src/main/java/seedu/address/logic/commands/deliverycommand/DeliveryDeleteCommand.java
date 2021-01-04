package seedu.address.logic.commands.deliverycommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryModel;

/**
 * Deletes a delivery identified using it's displayed index from the delivery book.
 */
public class DeliveryDeleteCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = "delete-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes delivery identified by the index number used in the displayed delivery list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Delivery: \n%1$s";

    private final Index targetIndex;

    public DeliveryDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Models models) throws CommandException {
        requireNonNull(models);
        requireNonNull(models.getDeliveryModel());
        DeliveryModel deliveryModel = models.getDeliveryModel();
        List<Delivery> lastShownList = deliveryModel.getFilteredAndSortedDeliveryList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        Delivery deliveryToDelete = lastShownList.get(targetIndex.getZeroBased());
        deliveryModel.deleteDelivery(deliveryToDelete);
        models.commit();
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, deliveryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeliveryDeleteCommand) other).targetIndex)); // state check
    }
}
