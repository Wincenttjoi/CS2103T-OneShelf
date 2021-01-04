package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveDateTime;

    private Pair<String, LocalDateTime> displayString;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter() {
        super(FXML);
        this.displayString = null;
        initialize();
    }

    /**
     * Sets the display text on status footer. Default display time of 3 seconds
     *
     * @param display the displayed text.
     */
    public void setDisplayString(String display) {
        //@@author tanweijie123-reused
        setDisplayString(display, 3);
    }

    /**
     * Sets the display text on status footer.
     *
     * @param display the displayed text.
     * @param seconds the amount of seconds to display
     */
    public void setDisplayString(String display, int seconds) {
        this.displayString = new Pair<>(display, LocalDateTime.now().plusSeconds(seconds));
    }

    /**
     * Initializes the local date time.
     */
    private void initialize() {
        //Solution below adapted from: https://stackoverflow.com/questions/42383857/javafx-live-time-and-date/42384436
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();
            checkDisplayValidity();
            if (displayString == null) {
                saveDateTime.setText(currentTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")));
            } else {
                saveDateTime.setText(currentTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"))
                        + " - " + displayString.getKey());
            }
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void checkDisplayValidity() {
        if (displayString != null) {
            if (displayString.getValue().isBefore(LocalDateTime.now())) {
                displayString = null;
            }
        }
    }

}
