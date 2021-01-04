package seedu.address.logic.parser.deliveryparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERYNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DAMITH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDeliveries.AARON_MANUAL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deliverycommand.DeliveryAddCommand;
import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;
import seedu.address.model.item.Name;
import seedu.address.testutil.DeliveryBuilder;

public class DeliveryAddCommandParserTest {
    private DeliveryAddCommandParser parser = new DeliveryAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Delivery expectedDelivery = new DeliveryBuilder(AARON_MANUAL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AARON + PHONE_DESC_AARON
                + ADDRESS_DESC_AARON + ORDER_DESC_AARON, new DeliveryAddCommand(expectedDelivery));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_DAMITH + NAME_DESC_AARON + PHONE_DESC_AARON
                + ADDRESS_DESC_AARON + ORDER_DESC_AARON, new DeliveryAddCommand(expectedDelivery));

        // multiple phone - last phone accepted
        assertParseSuccess(parser, NAME_DESC_AARON + PHONE_DESC_AARON + PHONE_DESC_AARON
                + ADDRESS_DESC_AARON + ORDER_DESC_AARON, new DeliveryAddCommand(expectedDelivery));

        // multiple address - last address accepted
        assertParseSuccess(parser, NAME_DESC_AARON + PHONE_DESC_AARON + ADDRESS_DESC_AARON
                + ADDRESS_DESC_AARON + ORDER_DESC_AARON, new DeliveryAddCommand(expectedDelivery));

        // multiple orders - last order accepted
        assertParseSuccess(parser, NAME_DESC_AARON + PHONE_DESC_AARON + ADDRESS_DESC_AARON
                + ORDER_DESC_AARON + ORDER_DESC_AARON, new DeliveryAddCommand(expectedDelivery));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_DAMITH + PHONE_DESC_DAMITH + ADDRESS_DESC_DAMITH + ORDER_DESC_DAMITH,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_DAMITH + VALID_PHONE_DAMITH + ADDRESS_DESC_DAMITH + ORDER_DESC_DAMITH,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_DAMITH + PHONE_DESC_DAMITH + VALID_ADDRESS_DAMITH + ORDER_DESC_DAMITH,
                expectedMessage);

        // missing order prefix
        assertParseFailure(parser, NAME_DESC_DAMITH + PHONE_DESC_DAMITH + ADDRESS_DESC_DAMITH + VALID_ORDER_DAMITH,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_DAMITH + VALID_PHONE_DAMITH
                        + VALID_ADDRESS_DAMITH + VALID_ORDER_DAMITH, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_DELIVERYNAME_DESC + PHONE_DESC_DAMITH + ADDRESS_DESC_DAMITH
                        + ORDER_DESC_DAMITH, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_DAMITH + INVALID_PHONE_DESC + ADDRESS_DESC_DAMITH
                        + ORDER_DESC_DAMITH, Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_DAMITH + PHONE_DESC_DAMITH + INVALID_ADDRESS_DESC
                        + ORDER_DESC_DAMITH, Address.MESSAGE_CONSTRAINTS);

        // invalid order
        assertParseFailure(parser, NAME_DESC_DAMITH + PHONE_DESC_DAMITH + ADDRESS_DESC_DAMITH
                        + INVALID_ORDER_DESC, Order.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DELIVERYNAME_DESC + PHONE_DESC_DAMITH + ADDRESS_DESC_DAMITH
                        + INVALID_ORDER_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_DAMITH + PHONE_DESC_DAMITH + ADDRESS_DESC_DAMITH
                        + ORDER_DESC_DAMITH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryAddCommand.MESSAGE_USAGE));
    }
}
