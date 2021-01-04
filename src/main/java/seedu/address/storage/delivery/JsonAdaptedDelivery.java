package seedu.address.storage.delivery;

/*import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;*/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;
import seedu.address.model.delivery.Time;

public class JsonAdaptedDelivery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Delivery's %s field is missing!";

    private final String name;
    private final String phone;
    private final String address;
    private final String order;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedDelivery} with the given Delivery details.
     */
    @JsonCreator
    public JsonAdaptedDelivery(@JsonProperty("name") String name,
                               @JsonProperty("phone") String phone,
                               @JsonProperty("address") String address,
                               @JsonProperty("order") String order,
                               @JsonProperty("time") String endTime) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.order = order;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Delivery} into this class for Jackson use.
     */
    public JsonAdaptedDelivery(Delivery source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        order = source.getOrder().value;
        endTime = source.getTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted item object into the model's {@code Delivery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted delivery.
     */
    public Delivery toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryName.class.getSimpleName()));
        }
        if (!DeliveryName.isValidName(name)) {
            throw new IllegalValueException(DeliveryName.MESSAGE_CONSTRAINTS);
        }
        final DeliveryName modelName = new DeliveryName(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (order == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Order.class.getSimpleName()));
        }
        if (!Order.isValidOrder(order)) {
            throw new IllegalValueException(Order.MESSAGE_CONSTRAINTS);
        }
        final Order modelOrder = new Order(order);

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }

        final Time modelTime = new Time("0", endTime);

        return new Delivery(modelName, modelPhone, modelAddress, modelOrder, modelTime);

    }



}
