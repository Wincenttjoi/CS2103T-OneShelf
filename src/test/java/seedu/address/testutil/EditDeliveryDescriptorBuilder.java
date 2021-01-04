package seedu.address.testutil;

import seedu.address.logic.commands.deliverycommand.DeliveryEditCommand;
import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;
import seedu.address.model.delivery.Time;


/**
 * A utility class to help with building EditDeliveryDescriptor objects.
 */
public class EditDeliveryDescriptorBuilder {

    private DeliveryEditCommand.EditDeliveryDescriptor descriptor;

    public EditDeliveryDescriptorBuilder() {
        descriptor = new DeliveryEditCommand.EditDeliveryDescriptor();
    }

    public EditDeliveryDescriptorBuilder(DeliveryEditCommand.EditDeliveryDescriptor descriptor) {
        this.descriptor = new DeliveryEditCommand.EditDeliveryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDeliveryDescriptor} with fields containing {@code Delivery}'s details
     */
    public EditDeliveryDescriptorBuilder(Delivery delivery) {
        descriptor = new DeliveryEditCommand.EditDeliveryDescriptor();
        descriptor.setDeliveryName(delivery.getName());
        descriptor.setPhone(delivery.getPhone());
        descriptor.setAddress(delivery.getAddress());
        descriptor.setOrder(delivery.getOrder());
    }

    /**
     * Sets the {@code Name} of the {@code EditDeliveryDescriptor} that we are building.
     */
    public EditDeliveryDescriptorBuilder withName(String name) {
        descriptor.setDeliveryName(new DeliveryName(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditDeliveryDescriptor} that we are building.
     */
    public EditDeliveryDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditDeliveryDescriptor} that we are building.
     */
    public EditDeliveryDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code order} of the {@code EditDeliveryDescriptor} that we are building.
     */
    public EditDeliveryDescriptorBuilder withOrder(String order) {
        descriptor.setOrder(new Order(order));
        return this;
    }

    /**
     * Sets the {@code order} of the {@code EditDeliveryDescriptor} that we are building.
     */
    public EditDeliveryDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time("0", time));
        return this;
    }

    public DeliveryEditCommand.EditDeliveryDescriptor build() {
        return descriptor;
    }
}
