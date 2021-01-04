package seedu.address.ui;

import static seedu.address.commons.core.Messages.HELP_START;
import static seedu.address.commons.core.Messages.HELP_SUMMARY;
import static seedu.address.commons.core.Messages.MESSAGE_HELP_ON_START;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.HelpCommandResult;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String TITLE = "OneShelf";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ItemListPanel itemListPanel;
    private ResultDisplay resultDisplay;
    private DeliveryListPanel deliveryListPanel;
    private HelpWindow helpWindow;
    private PreviewWindow previewWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem startHelpMenuItem;

    @FXML
    private MenuItem summaryHelpMenuItem;

    @FXML
    private StackPane itemListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane deliveryListPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        primaryStage.setResizable(false);
        primaryStage.setTitle(TITLE);

        setAccelerators();

        helpWindow = new HelpWindow();
        previewWindow = new PreviewWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(startHelpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(summaryHelpMenuItem, KeyCombination.valueOf("F2"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        itemListPanel = new ItemListPanel(logic.getFilteredAndSortedItemList());
        itemListPanelPlaceholder.getChildren().add(itemListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplay.setFeedbackToUser(MESSAGE_HELP_ON_START);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        deliveryListPanel = new DeliveryListPanel(logic.getFilteredAndSortedDeliveryList());
        deliveryListPanelPlaceholder.getChildren().add(deliveryListPanel.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter();
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Sets up the start help window page and opens it or focuses on it if it's already opened
     */
    @FXML
    public void handleHelpStart() {
        helpWindow.setText(HELP_START);
        handleHelp();
    }

    /**
     * Sets up the summary help window page and opens it or focuses on it if it's already opened
     */
    @FXML
    public void handleHelpSummary() {
        previewWindow.setPreviewText(HELP_SUMMARY);
        handlePreview();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    private void handlePreview() {
        if (!previewWindow.isShowing()) {
            previewWindow.show();
        } else {
            previewWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        previewWindow.hide();
        primaryStage.hide();
    }

    public ItemListPanel getItemListPanel() {
        return itemListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                HelpCommandResult helpCommandResult = (HelpCommandResult) commandResult;
                helpWindow.setText(helpCommandResult.getPopUpContent());
                handleHelp();
            }

            if (commandResult.isShowPreview()) {
                HelpCommandResult helpCommandResult = (HelpCommandResult) commandResult;
                previewWindow.setPreviewText(helpCommandResult.getPopUpContent());
                handlePreview();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
