package seedu.address.logic.parser.deliveryparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deliverycommand.DeliveryEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeliveryEditCommand object
 */
public class DeliveryEditCommandParser implements Parser<DeliveryEditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeliveryEditCommand
     * and returns an DeliveryEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ORDER, PREFIX_TIME);

        assert argMultimap != null;

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeliveryEditCommand.MESSAGE_USAGE), pe);
        }

        DeliveryEditCommand.EditDeliveryDescriptor editDeliveryDescriptor =
                new DeliveryEditCommand.EditDeliveryDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDeliveryDescriptor.setDeliveryName(
                    ParserUtil.parseDeliveryName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editDeliveryDescriptor.setPhone(
                    ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editDeliveryDescriptor.setAddress(
                    ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_ORDER).isPresent()) {
            editDeliveryDescriptor.setOrder(ParserUtil
                    .parseOrder(argMultimap.getValue(PREFIX_ORDER).get()));
        }

        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editDeliveryDescriptor.setTime(ParserUtil
                    .parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        if (!editDeliveryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(DeliveryEditCommand.MESSAGE_NOT_EDITED);
        }

        return new DeliveryEditCommand(index, editDeliveryDescriptor);
    }

}
