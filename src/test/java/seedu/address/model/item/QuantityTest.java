package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null Quantity number
        assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid Quantity numbers
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("Quantity")); // non-numeric
        assertFalse(Quantity.isValidQuantity("9p0")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("9 3")); // spaces within digits

        // valid Quantity numbers
        assertTrue(Quantity.isValidQuantity("1")); // exactly 1 numbers
        assertTrue(Quantity.isValidQuantity("93121534"));
        assertTrue(Quantity.isValidQuantity("123456789")); // long Quantity numbers
    }
}

