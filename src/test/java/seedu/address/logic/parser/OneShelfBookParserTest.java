package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.deliverycommand.DeliveryClearCommand;
import seedu.address.logic.commands.deliverycommand.DeliveryDeleteCommand;
import seedu.address.logic.commands.deliverycommand.DeliveryEditCommand;
import seedu.address.logic.commands.deliverycommand.DeliveryFindCommand;
import seedu.address.logic.commands.deliverycommand.DeliveryListCommand;
import seedu.address.logic.commands.help.HelpCommand;
import seedu.address.logic.commands.itemcommand.ItemAddCommand;
import seedu.address.logic.commands.itemcommand.ItemClearCommand;
import seedu.address.logic.commands.itemcommand.ItemDeleteCommand;
import seedu.address.logic.commands.itemcommand.ItemEditCommand;
import seedu.address.logic.commands.itemcommand.ItemFindCommand;
import seedu.address.logic.commands.itemcommand.ItemListCommand;
import seedu.address.logic.commands.itemcommand.ItemRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.predicate.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.item.Item;
import seedu.address.model.item.predicate.NameContainsKeywordsPredicate;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.DeliveryUtil;
import seedu.address.testutil.EditDeliveryDescriptorBuilder;
import seedu.address.testutil.EditItemDescriptorBuilder;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.ItemUtil;

public class OneShelfBookParserTest {

    private final OneShelfBookParser parser = new OneShelfBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Item item = new ItemBuilder().build();
        ItemAddCommand command = (ItemAddCommand) parser.parseCommand(ItemUtil.getAddCommand(item));
        assertEquals(new ItemAddCommand(item), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ItemClearCommand.COMMAND_WORD) instanceof ItemClearCommand);
        assertTrue(parser.parseCommand(ItemClearCommand.COMMAND_WORD + " 3") instanceof ItemClearCommand);

        assertTrue(parser.parseCommand(DeliveryClearCommand.COMMAND_WORD) instanceof DeliveryClearCommand);
        assertTrue(parser.parseCommand(DeliveryClearCommand.COMMAND_WORD + " 3") instanceof DeliveryClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        ItemDeleteCommand itemCommand = (ItemDeleteCommand) parser.parseCommand(
                ItemDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased());
        assertEquals(new ItemDeleteCommand(INDEX_FIRST_ITEM), itemCommand);

        DeliveryDeleteCommand deliveryCommand = (DeliveryDeleteCommand) parser.parseCommand(
                DeliveryDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased());
        assertEquals(new DeliveryDeleteCommand(INDEX_FIRST_ITEM), deliveryCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Item item = new ItemBuilder().build();
        ItemEditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder(item).build();
        ItemEditCommand command = (ItemEditCommand) parser.parseCommand(ItemEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ITEM.getOneBased() + " " + ItemUtil.getEditItemDescriptorDetails(descriptor));
        assertEquals(new ItemEditCommand(INDEX_FIRST_ITEM, descriptor), command);

        Delivery delivery = new DeliveryBuilder().build();
        DeliveryEditCommand.EditDeliveryDescriptor descriptorDelivery =
                new EditDeliveryDescriptorBuilder(delivery).build();
        DeliveryEditCommand command2 =
                (DeliveryEditCommand) parser.parseCommand(DeliveryEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ITEM.getOneBased() + " "
                        + DeliveryUtil.getEditDeliveryDescriptorDetails(descriptorDelivery));
        assertEquals(new DeliveryEditCommand(INDEX_FIRST_ITEM, descriptorDelivery), command2);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Collections.singletonList("CHICKEN");
        ItemFindCommand command = (ItemFindCommand) parser.parseCommand(
                ItemFindCommand.COMMAND_WORD + " " + "n/chicken");
        assertEquals(new ItemFindCommand(new NameContainsKeywordsPredicate(keywords)), command);

        List<String> deliveryKeywords = Collections.singletonList("KELVIN");
        DeliveryFindCommand deliveryCommand = (DeliveryFindCommand) parser.parseCommand(
                DeliveryFindCommand.COMMAND_WORD + " " + "n/Kelvin");
        assertEquals(new DeliveryFindCommand(
                new DeliveryNameContainsKeywordsPredicate(deliveryKeywords)), deliveryCommand);
    }

    @Test
    public void parseCommand_remove() throws Exception {
        assertTrue(parser.parseCommand(ItemRemoveCommand.COMMAND_WORD + " 1 q/10") instanceof ItemRemoveCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " " + HelpCommand.COMMAND_OPTION_SUMMARY)
                instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " " + HelpCommand.COMMAND_OPTION_START)
                instanceof HelpCommand);
        assertThrows(ParseException.class,
                HelpCommand.MESSAGE_INVALID_OPTION, () -> parser
                        .parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ItemListCommand.COMMAND_WORD) instanceof ItemListCommand);
        assertTrue(parser.parseCommand(ItemListCommand.COMMAND_WORD + " 3") instanceof ItemListCommand);

        assertTrue(parser.parseCommand(DeliveryListCommand.COMMAND_WORD) instanceof DeliveryListCommand);
        assertTrue(parser.parseCommand(DeliveryListCommand.COMMAND_WORD + " 3") instanceof DeliveryListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser
                .parseCommand("unknownCommand"));
    }
}
