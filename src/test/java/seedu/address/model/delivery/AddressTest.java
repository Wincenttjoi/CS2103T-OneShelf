package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null Address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid Address
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid Address
        assertTrue(Address.isValidAddress("orchard road*")); // contains non-alphanumeric characters
        assertTrue(Address.isValidAddress("^")); // only non-alphanumeric characters
        assertTrue(Address.isValidAddress("blk 223")); // alphabets only
        assertTrue(Address.isValidAddress("12345")); // numbers only
        assertTrue(Address.isValidAddress("345 jln pagar")); // alphanumeric characters
        assertTrue(Address.isValidAddress("BLK ORCHARD ROAD")); // with capital letters
        assertTrue(Address.isValidAddress("singapore blk jln burong kechil tanjong pajar street")); // long names
    }
}
