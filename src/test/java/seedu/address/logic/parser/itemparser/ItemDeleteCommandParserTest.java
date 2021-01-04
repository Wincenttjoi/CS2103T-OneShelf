package seedu.address.logic.parser.itemparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itemcommand.ItemDeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ItemDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ItemDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ItemDeleteCommandParserTest {

    private ItemDeleteCommandParser parser = new ItemDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new ItemDeleteCommand(INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
    }
}
