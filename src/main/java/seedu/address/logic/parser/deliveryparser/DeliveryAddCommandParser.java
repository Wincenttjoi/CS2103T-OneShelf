package seedu.address.logic.parser.deliveryparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.deliverycommand.DeliveryAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;
import seedu.address.model.delivery.Time;

public class DeliveryAddCommandParser implements Parser<DeliveryAddCommand> {

    private static final String DEFAULT_TIME_DELIVERY = "30";

    @Override
    public DeliveryAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ORDER, PREFIX_TIME);

        assert argMultimap != null;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryAddCommand.MESSAGE_USAGE));
        }

        DeliveryName deliveryName = ParserUtil.parseDeliveryName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Order order = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).orElse(DEFAULT_TIME_DELIVERY));

        Delivery delivery = new Delivery(deliveryName, phone, address, order, time);

        return new DeliveryAddCommand(delivery);
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
