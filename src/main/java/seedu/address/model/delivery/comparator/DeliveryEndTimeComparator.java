package seedu.address.model.delivery.comparator;

import java.util.Comparator;

import seedu.address.model.delivery.Delivery;

public class DeliveryEndTimeComparator implements Comparator<Delivery> {
    @Override
    public int compare(Delivery delivery1, Delivery delivery2) {

        if (delivery1.getEndTime().isBefore(delivery2.getEndTime())) {
            return -1;
        } else if (delivery1.getEndTime().isAfter(delivery2.getEndTime())) {
            return 1;
        } else {
            // when 2 deliveries have the same endTime
            return delivery1.getName().compareTo(delivery2.getName());
        }
    }
}
