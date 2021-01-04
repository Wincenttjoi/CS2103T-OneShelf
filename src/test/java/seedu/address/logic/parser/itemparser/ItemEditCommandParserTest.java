package seedu.address.logic.parser.itemparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUPPLIER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.SUPPLIER_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.SUPPLIER_DESC_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUPPLIER_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUPPLIER_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DUCK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itemcommand.ItemEditCommand;
import seedu.address.logic.commands.itemcommand.ItemEditCommand.EditItemDescriptor;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.item.Tag;
import seedu.address.testutil.EditItemDescriptorBuilder;

public class ItemEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemEditCommand.MESSAGE_USAGE);

    private ItemEditCommandParser parser = new ItemEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_CHICKEN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ItemEditCommand.MESSAGE_NOT_EDITED);

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
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_SUPPLIER_DESC, Supplier.MESSAGE_CONSTRAINTS); // invalid supplier
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + QUANTITY_DESC_DUCK + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Item} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_CHICKEN + TAG_DESC_DUCK + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_CHICKEN + TAG_EMPTY + TAG_DESC_DUCK, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_CHICKEN + TAG_DESC_DUCK, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + VALID_SUPPLIER_CHICKEN + VALID_QUANTITY_CHICKEN,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_DUCK + TAG_DESC_DUCK
                + SUPPLIER_DESC_CHICKEN + NAME_DESC_CHICKEN + TAG_DESC_CHICKEN;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_CHICKEN)
                .withQuantity(VALID_QUANTITY_DUCK).withSupplier(VALID_SUPPLIER_CHICKEN)
                .withTags(VALID_TAG_DUCK, VALID_TAG_CHICKEN).build();
        ItemEditCommand expectedCommand = new ItemEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_DUCK;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withQuantity(VALID_QUANTITY_DUCK)
                .build();
        ItemEditCommand expectedCommand = new ItemEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_CHICKEN;
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_CHICKEN).build();
        ItemEditCommand expectedCommand = new ItemEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_CHICKEN;
        descriptor = new EditItemDescriptorBuilder().withQuantity(VALID_QUANTITY_CHICKEN).build();
        expectedCommand = new ItemEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // supplier
        userInput = targetIndex.getOneBased() + SUPPLIER_DESC_CHICKEN;
        descriptor = new EditItemDescriptorBuilder().withSupplier(VALID_SUPPLIER_CHICKEN).build();
        expectedCommand = new ItemEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_CHICKEN;
        descriptor = new EditItemDescriptorBuilder().withTags(VALID_TAG_CHICKEN).build();
        expectedCommand = new ItemEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_CHICKEN + SUPPLIER_DESC_CHICKEN
                + TAG_DESC_CHICKEN + QUANTITY_DESC_CHICKEN + SUPPLIER_DESC_CHICKEN + TAG_DESC_CHICKEN
                + QUANTITY_DESC_DUCK + SUPPLIER_DESC_DUCK + TAG_DESC_DUCK;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withQuantity(VALID_QUANTITY_DUCK)
                .withSupplier(VALID_SUPPLIER_DUCK).withTags(VALID_TAG_CHICKEN, VALID_TAG_DUCK)
                .build();
        ItemEditCommand expectedCommand = new ItemEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + INVALID_QUANTITY_DESC + QUANTITY_DESC_DUCK;
        ItemEditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder()
                .withQuantity(VALID_QUANTITY_DUCK).build();
        ItemEditCommand expectedCommand = new ItemEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_QUANTITY_DESC + SUPPLIER_DESC_DUCK
                + QUANTITY_DESC_DUCK;
        descriptor = new EditItemDescriptorBuilder().withQuantity(VALID_QUANTITY_DUCK)
                .withSupplier(VALID_SUPPLIER_DUCK).build();
        expectedCommand = new ItemEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withTags().build();
        ItemEditCommand expectedCommand = new ItemEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
