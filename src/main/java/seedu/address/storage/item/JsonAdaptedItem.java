package seedu.address.storage.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Item;
import seedu.address.model.item.Metric;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.item.Tag;

/**
 * Jackson-friendly version of {@link Item}.
 */
class JsonAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    private final String name;
    private final String quantity;
    private final String supplier;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String maxQuantity;
    private final String metric;

    /**
     * Constructs a {@code JsonAdaptedItem} with the given item details.
     */
    @JsonCreator
    public JsonAdaptedItem(@JsonProperty("name") String name,
                           @JsonProperty("quantity") String quantity,
                           @JsonProperty("supplier") String supplier,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("maxQuantity") String maxQuantity,
                           @JsonProperty("metric") String metric) {
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.maxQuantity = maxQuantity;
        this.metric = metric;
    }

    /**
     * Converts a given {@code Item} into this class for Jackson use.
     */
    public JsonAdaptedItem(Item source) {
        name = source.getName().fullName;
        quantity = source.getQuantity().value;
        supplier = source.getSupplier().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        maxQuantity = source.getMaxQuantity().map(q -> q.value).orElse(null);
        metric = source.getMetric().map(m -> m.value).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted item object into the model's {@code Item} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item.
     */
    public Item toModelType() throws IllegalValueException {
        final List<Tag> itemTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            itemTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (supplier == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Supplier.class.getSimpleName()));
        }
        if (!Supplier.isValidSupplier(supplier)) {
            throw new IllegalValueException(Supplier.MESSAGE_CONSTRAINTS);
        }
        final Supplier modelSupplier = new Supplier(supplier);

        final Set<Tag> modelTags = new HashSet<>(itemTags);

        if (maxQuantity != null && !Quantity.isValidMaxQuantity(maxQuantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelMaxQuantity = maxQuantity == null ? null : new Quantity(maxQuantity);

        if (metric != null && !Metric.isValidMetric(metric)) {
            throw new IllegalValueException(Metric.MESSAGE_CONSTRAINTS);
        }
        final Metric modelMetric = metric == null ? null : new Metric(metric);

        return new Item(modelName, modelQuantity, modelSupplier, modelTags, modelMaxQuantity, modelMetric);
    }

}
