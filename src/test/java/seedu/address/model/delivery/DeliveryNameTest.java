package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class DeliveryNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new DeliveryName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> DeliveryName.isValidName(null));

        // invalid name
        assertFalse(DeliveryName.isValidName("")); // empty string
        assertFalse(DeliveryName.isValidName(" ")); // spaces only
        assertFalse(DeliveryName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(DeliveryName.isValidName("damith*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(DeliveryName.isValidName("marissa")); // alphabets only
        assertTrue(DeliveryName.isValidName("12345")); // numbers only
        assertTrue(DeliveryName.isValidName("5th son of obama")); // alphanumeric characters
        assertTrue(DeliveryName.isValidName("TRUMP")); // with capital letters
        assertTrue(DeliveryName.isValidName("hubert blaine wolfeschlegelsteinhausenbergerdorff sr")); // long names
    }
}
