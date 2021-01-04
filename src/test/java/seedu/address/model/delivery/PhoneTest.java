package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("^")); // only non-alphanumeric characters
        assertFalse(Phone.isValidPhone("damith*")); // contains non-alphanumeric characters
        assertFalse(Phone.isValidPhone("5th son of obama")); // alphanumeric characters
        assertFalse(Phone.isValidPhone("12")); // alphanumeric characters

        // valid phone
        assertTrue(Phone.isValidPhone("12345")); // numbers only
        assertTrue(Phone.isValidPhone("123")); // numbers only
    }
}
