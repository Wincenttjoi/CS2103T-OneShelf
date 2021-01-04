package seedu.address.model;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;

/**
 * API of a Model component
 */
public interface Model {
    int MODEL_DEFAULT_STATES_LIMIT = 20;
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Sets the maximum number of states that the model should keep
     */
    void setStatesLimit(int limit);

    /**
     * Commits the current model to the list of model states
     */
    void commit();

    /**
     * Reverts the current model to the previous one in the list of model states
     * @throws UndoRedoLimitReachedException if there is nothing left to undo
     */
    void undo() throws UndoRedoLimitReachedException;

    /**
     * Changes an undone model back to the previous one
     * @throws UndoRedoLimitReachedException if there is nothing left to redo
     */
    void redo() throws UndoRedoLimitReachedException;
}
