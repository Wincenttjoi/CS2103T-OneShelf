package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.history.History;
import seedu.address.history.HistoryManager;
import seedu.address.history.exception.HistoryException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int COMMAND_HISTORY_LIMIT = 20;

    private final CommandExecutor commandExecutor;
    private final History history;

    @FXML
    private TextField commandTextField;

    @FXML
    private Button submitCommand;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        history = makeCommandHistory();

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.setOnKeyPressed(event -> handleHistoryNavigation(event));
    }

    /**
     * Makes a {@code HistoryManager} object with COMMAND_HISTORY_LIMIT
     * @return {@code HistoryManager} object
     * @see seedu.address.ui.CommandBox#COMMAND_HISTORY_LIMIT
     */
    private HistoryManager makeCommandHistory() {
        HistoryManager tempHistory;
        try {
            tempHistory = new HistoryManager(COMMAND_HISTORY_LIMIT);
        } catch (HistoryException historyException) {
            tempHistory = null;
            System.err.println(historyException);
            System.exit(1);
        }

        return tempHistory;
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        history.addToHistory(commandTextField.getText());

        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the Up/Down button pressed event.
     */
    @FXML
    private void handleHistoryNavigation(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            commandTextField.setText(history.previousCommand().orElse(""));
            break;

        case DOWN:
            commandTextField.setText(history.nextCommand().orElse(""));
            break;

        default:
            break;
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
