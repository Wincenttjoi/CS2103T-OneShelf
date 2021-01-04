package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_ACKNOWLEDGEMENT = "Redone command";

    @Override
    public CommandResult execute(Models models) {
        requireNonNull(models);

        String message = MESSAGE_REDO_ACKNOWLEDGEMENT;

        try {
            models.redo();
        } catch (UndoRedoLimitReachedException e) {
            message = e.getMessage();
        }

        return new CommandResult(message);
    }
}
