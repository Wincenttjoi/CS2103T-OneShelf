package seedu.address.logic.parser.itemparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.itemcommand.ItemFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Item;
import seedu.address.model.item.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.item.predicate.SupplierContainsKeywordsPredicate;
import seedu.address.model.item.predicate.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ItemFindCommand object
 */
public class ItemFindCommandParser implements Parser<ItemFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ItemFindCommand
     * and returns a ItemFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUPPLIER, PREFIX_TAG);

        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_SUPPLIER, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemFindCommand.MESSAGE_USAGE));
        }

        List<Predicate<Item>> predicateList = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedArgs = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
            predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }

        if (argMultimap.getValue(PREFIX_SUPPLIER).isPresent()) {
            String trimmedArgs = ParserUtil.parseSupplier(argMultimap.getValue(PREFIX_SUPPLIER).get()).value;
            predicateList.add(new SupplierContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String trimmedArgs = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get()).tagName;
            predicateList.add(new TagContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }

        Predicate<Item> finalPredicate = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            finalPredicate = finalPredicate.and(predicateList.get(i));
        }
        return new ItemFindCommand(finalPredicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
