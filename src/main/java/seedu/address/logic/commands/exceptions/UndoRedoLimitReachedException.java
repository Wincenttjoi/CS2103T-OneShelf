package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of an {@link UndoCommand} or {@link RedoCommand}
 * when there are no more commands left to undo or redo.
 */
public class UndoRedoLimitReachedException extends Exception {
    public UndoRedoLimitReachedException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UndoRedoLimitReachedException}
     * with the specified detail {@code message} and {@code cause}.
     */
    public UndoRedoLimitReachedException(String message, Throwable cause) {
        super(message, cause);
    }
}
