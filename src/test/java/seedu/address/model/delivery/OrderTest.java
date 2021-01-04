package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class OrderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Order(null));
    }

    @Test
    public void constructor_invalidOrder_throwsIllegalArgumentException() {
        String invalidOrder = "";
        assertThrows(IllegalArgumentException.class, () -> new Order(invalidOrder));
    }

    @Test
    public void isValidOrder() {
        assertFalse(Order.isValidOrder("")); // empty string
        assertFalse(Order.isValidOrder(" ")); // spaces only

        assertTrue(Order.isValidOrder("char kway teow*")); // contains non-alphanumeric characters
        assertTrue(Order.isValidOrder("^")); // only non-alphanumeric characters
        assertTrue(Order.isValidOrder("mie goreng")); // alphabets only
        assertTrue(Order.isValidOrder("12345")); // numbers only
        assertTrue(Order.isValidOrder("fried rice x1")); // alphanumeric characters
        assertTrue(Order.isValidOrder("ICED KOPI")); // with capital letters
    }
}
