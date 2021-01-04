package seedu.address.logic.parser.deliveryparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERYNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AARON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deliverycommand.DeliveryEditCommand;
import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;
import seedu.address.model.item.Name;
import seedu.address.testutil.EditDeliveryDescriptorBuilder;

public class DeliveryEditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryEditCommand.MESSAGE_USAGE);

    private DeliveryEditCommandParser parser = new DeliveryEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AARON, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", DeliveryEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DELIVERYNAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid supplier
        assertParseFailure(parser, "1" + INVALID_ORDER_DESC, Order.MESSAGE_CONSTRAINTS); // invalid tag

        assertParseFailure(parser, "1" + PHONE_DESC_AARON + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ITEM;
        String userInput = targetIndex.getOneBased()
                + PHONE_DESC_AARON
                + NAME_DESC_AARON
                + ADDRESS_DESC_AARON
                + ORDER_DESC_AARON;
        DeliveryEditCommand.EditDeliveryDescriptor descriptor =
                new EditDeliveryDescriptorBuilder().withName(VALID_NAME_AARON)
                .withPhone(VALID_PHONE_AARON)
                .withAddress(VALID_ADDRESS_AARON)
                .withOrder(VALID_ORDER_AARON).build();
        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AARON;

        DeliveryEditCommand.EditDeliveryDescriptor descriptor =
                new EditDeliveryDescriptorBuilder()
                        .withPhone(VALID_PHONE_AARON)
                        .build();
        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AARON;
        DeliveryEditCommand.EditDeliveryDescriptor descriptor =
                new EditDeliveryDescriptorBuilder()
                        .withName(VALID_NAME_AARON)
                        .build();
        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AARON;
        descriptor = new EditDeliveryDescriptorBuilder().withPhone(VALID_PHONE_AARON).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AARON;
        descriptor = new EditDeliveryDescriptorBuilder().withAddress(VALID_ADDRESS_AARON).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // order
        userInput = targetIndex.getOneBased() + ORDER_DESC_AARON;
        descriptor = new EditDeliveryDescriptorBuilder().withOrder(VALID_ORDER_AARON).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AARON + PHONE_DESC_AARON + ADDRESS_DESC_AARON
                    + ORDER_DESC_AARON;

        DeliveryEditCommand.EditDeliveryDescriptor descriptor =
                new EditDeliveryDescriptorBuilder().withName(VALID_NAME_AARON)
                .withPhone(VALID_PHONE_AARON)
                .withAddress(VALID_ADDRESS_AARON)
                .withOrder(VALID_ORDER_AARON)
                .build();
        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_AARON;
        DeliveryEditCommand.EditDeliveryDescriptor descriptor = new EditDeliveryDescriptorBuilder()
                .withPhone(VALID_PHONE_AARON).build();
        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + ORDER_DESC_AARON
                + PHONE_DESC_AARON;
        descriptor = new EditDeliveryDescriptorBuilder().withPhone(VALID_PHONE_AARON)
                .withOrder(VALID_ORDER_AARON).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
