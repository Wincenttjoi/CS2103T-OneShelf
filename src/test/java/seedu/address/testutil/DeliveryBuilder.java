package seedu.address.testutil;

import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;
import seedu.address.model.delivery.Time;

/**
 * A utility class to help with building Delivery objects.
 */
public class DeliveryBuilder {

    public static final String DEFAULT_DELIVERY_NAME = "Damith";
    public static final String DEFAULT_PHONE = "91231231";
    public static final String DEFAULT_ADDRESS = "Jln Parang Tritis No. 92 Orchard Road";
    public static final String DEFAULT_ORDER = "Fried Rice 1x, Iced Kopi less sugar x1";
    public static final String DEFAULT_ENDTIME = "28 October 2020 00:00:00 000";

    private DeliveryName name;
    private Phone phone;
    private Address address;
    private Order order;
    private Time time;

    /**
     * Creates a {@code DeliveryBuilder} with the default details.
     */
    public DeliveryBuilder() {
        name = new DeliveryName(DEFAULT_DELIVERY_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        order = new Order(DEFAULT_ORDER);
        time = new Time("0", DEFAULT_ENDTIME);
    }

    /**
     * Initializes the DeliveryBuilder with the data of {@code Delivery}.
     */
    public DeliveryBuilder(Delivery deliveryToCopy) {
        name = deliveryToCopy.getName();
        phone = deliveryToCopy.getPhone();
        address = deliveryToCopy.getAddress();
        order = deliveryToCopy.getOrder();
        time = deliveryToCopy.getTime();
    }

    /**
     * Sets the {@code Name} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withName(String name) {
        this.name = new DeliveryName(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Order} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withOrder(String order) {
        this.order = new Order(order);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withTime(String time) {
        this.time = new Time("0", time);
        return this;
    }

    public Delivery build() {
        return new Delivery(name, phone, address, order, time);
    }

}
