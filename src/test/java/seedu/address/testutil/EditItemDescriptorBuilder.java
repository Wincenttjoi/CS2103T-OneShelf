package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.itemcommand.ItemEditCommand;
import seedu.address.logic.commands.itemcommand.ItemEditCommand.EditItemDescriptor;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.item.Tag;

/**
 * A utility class to help with building EditItemDescriptor objects.
 */
public class EditItemDescriptorBuilder {

    private EditItemDescriptor descriptor;

    public EditItemDescriptorBuilder() {
        descriptor = new ItemEditCommand.EditItemDescriptor();
    }

    public EditItemDescriptorBuilder(ItemEditCommand.EditItemDescriptor descriptor) {
        this.descriptor = new EditItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditItemDescriptor} with fields containing {@code item}'s details
     */
    public EditItemDescriptorBuilder(Item item) {
        descriptor = new ItemEditCommand.EditItemDescriptor();
        descriptor.setName(item.getName());
        descriptor.setQuantity(item.getQuantity());
        descriptor.setSupplier(item.getSupplier());
        descriptor.setTags(item.getTags());
        descriptor.setMaxQuantity(item.getMaxQuantity().orElse(null));
    }

    /**
     * Sets the {@code Name} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code Supplier} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withSupplier(String supplier) {
        descriptor.setSupplier(new Supplier(supplier));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditItemDescriptor}
     * that we are building.
     */
    public EditItemDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code maxQuantity} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withMaxQuantity(String maxQuantity) {
        descriptor.setMaxQuantity(new Quantity(maxQuantity));
        return this;
    }

    public ItemEditCommand.EditItemDescriptor build() {
        return descriptor;
    }
}
