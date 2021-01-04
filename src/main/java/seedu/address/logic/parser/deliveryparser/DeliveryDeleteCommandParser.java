package seedu.address.logic.parser.deliveryparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deliverycommand.DeliveryDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeliveryDeleteCommand object
 */
public class DeliveryDeleteCommandParser implements Parser<DeliveryDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeliveryDeleteCommand
     * and returns a DeliveryDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeliveryDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
