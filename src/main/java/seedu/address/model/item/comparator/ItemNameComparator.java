package seedu.address.model.item.comparator;

import java.util.Comparator;

import seedu.address.model.item.Item;

public class ItemNameComparator implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.getName().compareTo(item2.getName());
    }
}
