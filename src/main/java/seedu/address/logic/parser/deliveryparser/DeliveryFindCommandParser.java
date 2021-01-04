package seedu.address.logic.parser.deliveryparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.deliverycommand.DeliveryFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.delivery.predicate.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.delivery.predicate.OrderContainsKeywordsPredicate;
import seedu.address.model.delivery.predicate.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DeliveryFindCommand object
 */
public class DeliveryFindCommandParser implements Parser<DeliveryFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeliveryFindCommand
     * and returns a DeliveryFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_ORDER);

        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryFindCommand.MESSAGE_USAGE));
        }

        List<Predicate<Delivery>> predicateList = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedArgs = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
            predicateList.add(new DeliveryNameContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String trimmedArgs = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()).value;
            predicateList.add(new AddressContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String trimmedArgs = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()).value;
            predicateList.add(new PhoneContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }

        if (argMultimap.getValue(PREFIX_ORDER).isPresent()) {
            String trimmedArgs = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).get()).value;
            predicateList.add(new OrderContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }

        Predicate<Delivery> finalPredicate = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            finalPredicate = finalPredicate.and(predicateList.get(i));
        }

        return new DeliveryFindCommand(finalPredicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
