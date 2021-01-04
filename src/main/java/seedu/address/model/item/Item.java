package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a Item in the inventory book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity fields
    private final Name name;
    private final Supplier supplier;

    // Data fields
    private final Quantity quantity;
    private final Set<Tag> tags = new HashSet<>();
    private final Optional<Quantity> maxQuantity;
    private final Optional<Metric> metric;

    /**
     * Every field must be present and not null.
     */
    public Item(Name name, Quantity quantity, Supplier supplier, Set<Tag> tags, Quantity maxQuantity, Metric metric) {
        requireAllNonNull(name, quantity, supplier, tags);
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
        this.tags.addAll(tags);
        this.maxQuantity = Optional.ofNullable(maxQuantity);
        this.metric = Optional.ofNullable(metric);
    }

    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Optional<Quantity> getMaxQuantity() {
        return maxQuantity;
    }

    public Optional<Metric> getMetric() {
        return metric;
    }

    /**
     * Returns true if both items of the same name have the same supplier.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getName().equals(getName())
                && otherItem.getSupplier().equals(getSupplier());
    }

    /**
     * Returns true if both items have the same name and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getName().equals(getName())
                && otherItem.getQuantity().equals(getQuantity())
                && otherItem.getSupplier().equals(getSupplier())
                && otherItem.getTags().equals(getTags())
                && otherItem.getMaxQuantity().equals(getMaxQuantity());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, quantity, supplier, tags, maxQuantity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nName: ")
                .append(getName())
                .append("\n")
                .append("Quantity: ")
                .append(getQuantity())
                .append("\n")
                .append("Supplier: ")
                .append(getSupplier())
                .append("\n")
                .append("Tags: ");
        getTags().forEach(x -> builder.append(x).append(" "));
        if (maxQuantity.isPresent()) {
            builder.append("\n")
                    .append("Max Quantity: ")
                    .append(getMaxQuantity().get().value);
        }

        return builder.toString();
    }

}
