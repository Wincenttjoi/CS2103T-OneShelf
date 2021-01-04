package seedu.address.logic.parser.itemparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_METRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.itemcommand.ItemAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Item;
import seedu.address.model.item.Metric;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.item.Tag;

/**
 * Parses input arguments and creates a new ItemAddCommand object
 */
public class ItemAddCommandParser implements Parser<ItemAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ItemAddCommand
     * and returns an ItemAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_SUPPLIER, PREFIX_TAG,
                        PREFIX_MAX_QUANTITY, PREFIX_METRIC);

        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));
        }


        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Supplier supplier = ParserUtil.parseSupplier(argMultimap.getValue(PREFIX_SUPPLIER).orElse("No Supplier"));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        String maxQ = argMultimap.getValue(PREFIX_MAX_QUANTITY).orElse(null);
        Quantity maxQuantity = maxQ == null ? null : ParserUtil.parseMaxQuantity(maxQ);
        String met = argMultimap.getValue(PREFIX_METRIC).orElse(null);
        Metric metric = met == null ? null : ParserUtil.parseMetric(met);

        Item item = new Item(name, quantity, supplier, tagList, maxQuantity, metric);

        return new ItemAddCommand(item);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
