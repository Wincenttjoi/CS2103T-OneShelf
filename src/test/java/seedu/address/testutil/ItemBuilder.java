package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.item.Item;
import seedu.address.model.item.Metric;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.item.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "Chicken";
    public static final String DEFAULT_QUANTITY = "2";
    public static final String DEFAULT_SUPPLIER = "NTUC";
    public static final String DEFAULT_METRIC = "kg";

    private Name name;
    private Quantity quantity;
    private Supplier supplier;
    private Set<Tag> tags;
    private Quantity maxQuantity;
    private Metric metric;

    /**
     * Creates a {@code ItemBuilder} with the default details.
     */
    public ItemBuilder() {
        name = new Name(DEFAULT_NAME);
        quantity = new Quantity(DEFAULT_QUANTITY);
        supplier = new Supplier(DEFAULT_SUPPLIER);
        tags = new HashSet<>();
        maxQuantity = null;
        metric = null;
    }

    /**
     * Initializes the ItemBuilder with the data of {@code itemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getName();
        quantity = itemToCopy.getQuantity();
        supplier = itemToCopy.getSupplier();
        tags = new HashSet<>(itemToCopy.getTags());
        maxQuantity = itemToCopy.getMaxQuantity().orElse(null);
        metric = itemToCopy.getMetric().orElse(null);
    }

    /**
     * Sets the {@code Name} of the {@code Item} that we are building.
     */
    public ItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Item} that we are building.
     */
    public ItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Supplier} of the {@code Item} that we are building.
     */
    public ItemBuilder withSupplier(String supplier) {
        this.supplier = new Supplier(supplier);
        return this;
    }

    /**
     * Sets the {@code quantity} of the {@code Item} that we are building.
     */
    public ItemBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code maxQuantity} of the {@code Item} that we are building.
     */
    public ItemBuilder withMaxQuantity(String maxQuantity) {
        this.maxQuantity = new Quantity(maxQuantity);
        return this;
    }

    /**
     * Sets the {@code Metric} of the {@code Item} that we are building.
     */
    public ItemBuilder withMetric(String metric) {
        this.metric = new Metric(metric);
        return this;
    }

    public Item build() {
        return new Item(name, quantity, supplier, tags, maxQuantity, metric);
    }

}
