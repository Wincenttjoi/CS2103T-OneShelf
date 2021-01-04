package seedu.address.logic.parser.itemparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.itemcommand.ItemRemoveCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Quantity;


public class RemoveCommandParser implements Parser<ItemRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommand
     * and returns a RemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY);

        assert argMultimap != null;

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ItemRemoveCommand.MESSAGE_USAGE), ive);
        }

        if (!isQuantityPresent(argMultimap, PREFIX_QUANTITY)) {
            throw new ParseException(ItemRemoveCommand.MESSAGE_NO_QUANTITY);
        }

        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

        return new ItemRemoveCommand(index, quantity);
    }

    /**
     * Returns true if Prefix Quantity does not contains empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isQuantityPresent(ArgumentMultimap argumentMultimap, Prefix quantity) {
        return Stream.of(quantity).allMatch(prefix -> argumentMultimap.getValue(quantity).isPresent());
    }

}
