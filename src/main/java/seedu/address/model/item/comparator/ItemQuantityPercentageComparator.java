package seedu.address.model.item.comparator;

import java.util.Comparator;
import java.util.Optional;

import seedu.address.model.item.Item;
import seedu.address.model.item.Quantity;

public class ItemQuantityPercentageComparator implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        Quantity quantity1 = item1.getQuantity();
        Quantity quantity2 = item2.getQuantity();
        Optional<Quantity> maxQuantity1 = item1.getMaxQuantity();
        Optional<Quantity> maxQuantity2 = item2.getMaxQuantity();

        // orElse(Double.MAX_VALUE) flushes item to the end of the list as it does not have a maxQuantity
        double percentageQuantity1 = maxQuantity1.map(maxQ -> quantity1.divideBy(maxQ))
                .orElse(Double.MAX_VALUE);
        double percentageQuantity2 = maxQuantity2.map(maxQ -> quantity2.divideBy(maxQ))
                .orElse(Double.MAX_VALUE);

        if (percentageQuantity1 < percentageQuantity2) {
            return -1;
        } else if (percentageQuantity1 > percentageQuantity2) {
            return 1;
        } else {
            return quantity1.compareTo(quantity2);
        }
    }
}
