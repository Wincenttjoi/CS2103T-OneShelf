package seedu.address.storage.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.ReadOnlyDeliveryBook;

/**
 * An Immutable DeliveryBook that is serializable to JSON format.
 */
@JsonRootName(value = "deliverybook")
public class JsonSerializableDeliveryBook {

    private final List<JsonAdaptedDelivery> deliveries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDeliveryBook} with the given deliveries.
     */
    @JsonCreator
    public JsonSerializableDeliveryBook(@JsonProperty("deliveries") List<JsonAdaptedDelivery> deliveries) {
        this.deliveries.addAll(deliveries);
    }

    /**
     * Converts a given {@code ReadOnlyDeliveryBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInventoryBook}.
     */
    public JsonSerializableDeliveryBook(ReadOnlyDeliveryBook source) {
        deliveries.addAll(source.getDeliveryList().stream().map(JsonAdaptedDelivery::new).collect(Collectors.toList()));
    }

    /**
     * Converts this delivery book into the model's {@code DeliveryBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DeliveryBook toModelType() throws IllegalValueException {
        DeliveryBook deliveryBook = new DeliveryBook();
        for (JsonAdaptedDelivery jsonAdaptedDelivery : deliveries) {
            Delivery delivery = jsonAdaptedDelivery.toModelType();
            deliveryBook.addDelivery(delivery);
        }
        return deliveryBook;
    }
}
