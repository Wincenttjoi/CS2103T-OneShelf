package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.deliverycommand.DeliveryAddCommand;
import seedu.address.logic.commands.deliverycommand.DeliveryEditCommand;
import seedu.address.model.delivery.Delivery;

/**
 * A utility class for Delivery.
 */
public class DeliveryUtil {

    /**
     * Returns an add command string for adding the {@code delivery}.
     */
    public static String getAddCommand(Delivery delivery) {
        return DeliveryAddCommand.COMMAND_WORD + " " + getDeliveryDetails(delivery);
    }

    /**
     * Returns the part of command string for the given {@code delivery}'s details.
     */
    public static String getDeliveryDetails(Delivery delivery) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + delivery.getName().fullName + " ");
        sb.append(PREFIX_PHONE + delivery.getPhone().value + " ");
        sb.append(PREFIX_ADDRESS + delivery.getAddress().value + " ");
        sb.append(PREFIX_ORDER + delivery.getOrder().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDeliveryDescriptor}'s details.
     */
    public static String getEditDeliveryDescriptorDetails(DeliveryEditCommand.EditDeliveryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDeliveryName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getOrder().ifPresent(order -> sb.append(PREFIX_ORDER).append(order.value).append(" "));

        return sb.toString();
    }
}
