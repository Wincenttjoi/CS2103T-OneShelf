package seedu.address.model.deliverymodel;

import javafx.collections.ObservableList;
import seedu.address.model.delivery.Delivery;

public interface ReadOnlyDeliveryBook {

    /**
     * Returns an unmodifiable view of the delivery list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Delivery> getDeliveryList();
}
