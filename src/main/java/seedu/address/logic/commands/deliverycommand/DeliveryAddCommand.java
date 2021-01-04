package seedu.address.logic.commands.deliverycommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryModel;


/**
 * Adds a delivery to the delivery book.
 */
public class DeliveryAddCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = "add-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a delivery to the delivery book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ORDER + "ORDER "
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alex Yeoh "
            + PREFIX_PHONE + "87438807 "
            + PREFIX_ADDRESS + "Blk 30 Geylang Street 29, #06-40 "
            + PREFIX_ORDER + "2x Chicken Rice, 1x Ice Milo";

    public static final String MESSAGE_SUCCESS = "New delivery added: %1$s";
    public static final String MESSAGE_DUPLICATE_DELIVERY = "This delivery already existed in the delivery book";


    private final Delivery toAdd;

    /**
     * Creates an DeliveryAddCommand to add the specified {@code Delivery}
     */
    public DeliveryAddCommand(Delivery item) {
        requireNonNull(item);
        toAdd = item;
    }

    @Override
    public CommandResult execute(Models models) {
        requireNonNull(models);
        requireNonNull(models.getDeliveryModel());
        DeliveryModel deliveryModel = models.getDeliveryModel();

        deliveryModel.addDelivery(toAdd);
        models.commit();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryAddCommand // instanceof handles nulls
                && toAdd.equals(((DeliveryAddCommand) other).toAdd));
    }
}
