package seedu.address.logic.commands.deliverycommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AARON;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditDeliveryDescriptorBuilder;

public class EditDeliveryDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        DeliveryEditCommand.EditDeliveryDescriptor descriptorWithSameValues =
                new DeliveryEditCommand.EditDeliveryDescriptor(DESC_DAMITH);

        assertTrue(DESC_DAMITH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DAMITH.equals(DESC_DAMITH));

        // null -> returns false
        assertFalse(DESC_DAMITH.equals(null));

        // different types -> returns false
        assertFalse(DESC_DAMITH.equals(5));

        // different values -> returns false
        assertFalse(DESC_DAMITH.equals(DESC_AARON));

        // different name -> returns false
        DeliveryEditCommand.EditDeliveryDescriptor editedDamith = new EditDeliveryDescriptorBuilder(DESC_DAMITH)
                .withName(VALID_NAME_AARON).build();
        assertFalse(DESC_DAMITH.equals(editedDamith));

        // different phone -> returns false
        editedDamith = new EditDeliveryDescriptorBuilder(DESC_DAMITH).withPhone(VALID_PHONE_AARON).build();
        assertFalse(DESC_DAMITH.equals(editedDamith));

        // different order -> returns false
        editedDamith = new EditDeliveryDescriptorBuilder(DESC_DAMITH).withOrder(VALID_ORDER_AARON).build();
        assertFalse(DESC_DAMITH.equals(editedDamith));

        // different address -> returns false
        editedDamith = new EditDeliveryDescriptorBuilder(DESC_DAMITH).withAddress(VALID_ADDRESS_AARON).build();
        assertFalse(DESC_DAMITH.equals(editedDamith));
    }
}
