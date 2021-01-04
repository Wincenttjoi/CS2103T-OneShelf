package seedu.address.logic.commands;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Inventory Book as requested ...";

    @Override
    public CommandResult execute(Models models) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
    }

}
