package seedu.address.logic.parser.itemparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.SUPPLIER_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DUCK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itemcommand.ItemFindCommand;
import seedu.address.model.item.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.item.predicate.SupplierContainsKeywordsPredicate;
import seedu.address.model.item.predicate.TagContainsKeywordsPredicate;

public class ItemFindCommandParserTest {

    private ItemFindCommandParser parser = new ItemFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ItemFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ItemFindCommand expectedFindCommand =
                new ItemFindCommand(new NameContainsKeywordsPredicate(Arrays.asList("CHICKEN", "DUCK")));
        assertParseSuccess(parser, NAME_DESC_CHICKEN + " " + VALID_NAME_DUCK, expectedFindCommand);
    }

    @Test
    public void parse_validSupplierArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ItemFindCommand expectedFindCommand = new ItemFindCommand(new SupplierContainsKeywordsPredicate(
                Arrays.asList("GIANT")));
        assertParseSuccess(parser, SUPPLIER_DESC_CHICKEN, expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ItemFindCommand expectedFindCommand = new ItemFindCommand(new TagContainsKeywordsPredicate(
                Arrays.asList("poultry")));
        assertParseSuccess(parser, TAG_DESC_CHICKEN, expectedFindCommand);
    }


}
