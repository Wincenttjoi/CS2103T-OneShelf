package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.deliverycommand.DeliveryAddCommand;
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
import seedu.address.logic.parser.deliveryparser.DeliveryAddCommandParser;
import seedu.address.logic.parser.deliveryparser.DeliveryDeleteCommandParser;
import seedu.address.logic.parser.deliveryparser.DeliveryEditCommandParser;
import seedu.address.logic.parser.deliveryparser.DeliveryFindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.itemparser.ItemAddCommandParser;
import seedu.address.logic.parser.itemparser.ItemDeleteCommandParser;
import seedu.address.logic.parser.itemparser.ItemEditCommandParser;
import seedu.address.logic.parser.itemparser.ItemFindCommandParser;
import seedu.address.logic.parser.itemparser.RemoveCommandParser;

/**
 * Parses user input.
 */
public class OneShelfBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        // Inventory command words
        case ItemAddCommand.COMMAND_WORD:
            return new ItemAddCommandParser().parse(arguments);

        case ItemEditCommand.COMMAND_WORD:
            return new ItemEditCommandParser().parse(arguments);

        case ItemRemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(arguments);

        case ItemDeleteCommand.COMMAND_WORD:
            return new ItemDeleteCommandParser().parse(arguments);

        case ItemClearCommand.COMMAND_WORD:
            return new ItemClearCommand();

        case ItemFindCommand.COMMAND_WORD:
            return new ItemFindCommandParser().parse(arguments);

        case ItemListCommand.COMMAND_WORD:
            return new ItemListCommand();

            // Delivery command words
        case DeliveryAddCommand.COMMAND_WORD:
            return new DeliveryAddCommandParser().parse(arguments);

        case DeliveryEditCommand.COMMAND_WORD:
            return new DeliveryEditCommandParser().parse(arguments);

        case DeliveryDeleteCommand.COMMAND_WORD:
            return new DeliveryDeleteCommandParser().parse(arguments);

        case DeliveryFindCommand.COMMAND_WORD:
            return new DeliveryFindCommandParser().parse(arguments);

        case DeliveryListCommand.COMMAND_WORD:
            return new DeliveryListCommand();

        case DeliveryClearCommand.COMMAND_WORD:
            return new DeliveryClearCommand();

            // General command words
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments.trim());

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
