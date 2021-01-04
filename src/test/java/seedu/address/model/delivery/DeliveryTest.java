package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AARON;
import static seedu.address.testutil.TypicalDeliveries.KELVIN;
import static seedu.address.testutil.TypicalDeliveries.MARCUS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;



public class DeliveryTest {

    @Test
    public void equals() {
        // same values -> returns true
        Delivery kelvinCopy = new DeliveryBuilder(KELVIN).build();
        assertTrue(KELVIN.equals(kelvinCopy));

        // same object -> returns true
        assertTrue(KELVIN.equals(KELVIN));

        // null -> returns false
        assertFalse(KELVIN.equals(null));

        // different type -> returns false
        assertFalse(KELVIN.equals(5));

        // different Item -> returns false
        assertFalse(KELVIN.equals(MARCUS));

        // different name -> returns false
        Delivery editedKelvin = new DeliveryBuilder(KELVIN).withName(VALID_NAME_DAMITH).build();
        assertFalse(KELVIN.equals(editedKelvin));

        // different address -> returns false
        editedKelvin = new DeliveryBuilder(KELVIN).withAddress(VALID_ADDRESS_AARON).build();
        assertFalse(KELVIN.equals(editedKelvin));

        // different phone -> returns false
        editedKelvin = new DeliveryBuilder(KELVIN).withPhone(VALID_PHONE_AARON).build();
        assertFalse(KELVIN.equals(editedKelvin));

        // different order -> returns false
        editedKelvin = new DeliveryBuilder(KELVIN).withOrder(VALID_ORDER_AARON).build();
        assertFalse(KELVIN.equals(editedKelvin));
    }
}
