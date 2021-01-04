package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_ACKNOWLEDGEMENT = "Reverted to previous state";

    @Override
    public CommandResult execute(Models models) {
        requireNonNull(models);

        String message = MESSAGE_UNDO_ACKNOWLEDGEMENT;

        try {
            models.undo();
        } catch (UndoRedoLimitReachedException e) {
            message = e.getMessage();
        }

        return new CommandResult(message);
    }
}
