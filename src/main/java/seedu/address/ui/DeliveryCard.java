package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import seedu.address.model.delivery.Delivery;

/**
 * An UI component that displays information of a {@code Item}.
 */
public class DeliveryCard extends UiPart<Region> {

    private static final String FXML = "DeliveryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Delivery delivery;
    private LocalDateTime endTime;

    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label order;
    @FXML
    private Text time;


    /**
     * Creates a {@code ItemCode} with the given {@code Item} and index to display.
     */
    public DeliveryCard(Delivery delivery, int displayedIndex) {
        super(FXML);
        this.delivery = delivery;
        id.setText(displayedIndex + ". ");
        name.setText(delivery.getName().toString());
        phone.setText(delivery.getPhone().toString());
        address.setText(delivery.getAddress().toString());
        order.setText(delivery.getOrder().toString());
        time.setText(delivery.getTime().toString());

        endTime = delivery.getEndTime();
        initializeDeliveryCountdown();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryCard)) {
            return false;
        }

        // state check
        //TODO: Update this in the future
        DeliveryCard card = (DeliveryCard) other;

        return card == other;
    }

    /**
     * Initializes the local date time.
     */
    private void initializeDeliveryCountdown() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();

            LocalDateTime tempDateTime = LocalDateTime.from(currentTime);

            long days = tempDateTime.until(endTime, ChronoUnit.DAYS);
            tempDateTime = tempDateTime.plusDays(days);

            long hours = tempDateTime.until(endTime, ChronoUnit.HOURS);
            tempDateTime = tempDateTime.plusHours(hours);

            long minutes = tempDateTime.until(endTime, ChronoUnit.MINUTES);
            tempDateTime = tempDateTime.plusMinutes(minutes);

            long seconds = tempDateTime.until(endTime, ChronoUnit.SECONDS);

            if ((minutes < 0 || seconds < 0)) { // OVERDUE BY: XXmin XXsec (in red)
                String timeString = "OVERDUE BY: "
                        + (days < 0 ? (-1 * days) + "days " : "")
                        + (hours < 0 ? (-1 * hours) + "hours " : "")
                        + (minutes < 0 ? (-1 * minutes) : minutes)
                        + "min "
                        + (seconds < 0 ? (-1 * seconds) : seconds)
                        + "sec";
                time.setText(timeString);
                time.setFill(Color.web("#f24e6c")); // light red
            } else { // DELIVER BY: XXmin XXsec (in green)
                String timeString = "DELIVER BY: "
                        + (days > 0 ? days + "days " : "")
                        + (hours > 0 ? hours + "hours " : "")
                        + minutes
                        + "min "
                        + seconds
                        + "sec";
                time.setText(timeString);
                if (minutes < 10) {
                    time.setFill(Color.ORANGE);
                } else {
                    time.setFill(Color.LIGHTGREEN);
                }
            }
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
