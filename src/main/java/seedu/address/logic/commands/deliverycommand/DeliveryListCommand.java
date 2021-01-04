package seedu.address.logic.commands.deliverycommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.deliverymodel.DeliveryModel.PREDICATE_SHOW_ALL_DELIVERIES;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;

/**
 * Lists all items in the delivery book to the user.
 */
public class DeliveryListCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = "list-d";

    public static final String MESSAGE_SUCCESS = "Listed all deliveries";


    @Override
    public CommandResult execute(Models models) {
        requireNonNull(models);
        requireNonNull(models.getDeliveryModel());

        models.getDeliveryModel().updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
