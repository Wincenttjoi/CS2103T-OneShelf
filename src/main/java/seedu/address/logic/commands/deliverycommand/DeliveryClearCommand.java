package seedu.address.logic.commands.deliverycommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.DeliveryModel;

/**
 * Clears the delivery book.
 */
public class DeliveryClearCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = "clear-d";
    public static final String MESSAGE_SUCCESS = "Delivery book has been cleared!";

    @Override
    public CommandResult execute(Models models) throws CommandException {
        requireNonNull(models);
        requireNonNull(models.getDeliveryModel());

        DeliveryModel deliveryModel = models.getDeliveryModel();
        deliveryModel.setDeliveryBook(new DeliveryBook());
        models.commit();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
