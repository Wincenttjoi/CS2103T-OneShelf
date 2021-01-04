package seedu.address.storage.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.delivery.JsonAdaptedDelivery.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.KELVIN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;

public class JsonAdaptedDeliveryTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_ORDER = " ";

    private static final String VALID_NAME = KELVIN.getName().toString();
    private static final String VALID_PHONE = KELVIN.getPhone().toString();
    private static final String VALID_ADDRESS = KELVIN.getAddress().toString();
    private static final String VALID_ORDER = KELVIN.getOrder().toString();
    private static final String VALID_ENDTIME = KELVIN.getTime().toString();

    @Test
    public void toModelType_validDeliveryDetails_returnsDelivery() throws Exception {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(KELVIN);
        assertEquals(KELVIN, delivery.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(INVALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_ORDER, VALID_ENDTIME);
        String expectedMessage = DeliveryName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(null, VALID_PHONE, VALID_ADDRESS, VALID_ADDRESS, VALID_ENDTIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_ADDRESS, VALID_ENDTIME);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_NAME, null, VALID_ADDRESS, VALID_ORDER, VALID_ENDTIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_ORDER, VALID_ENDTIME);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_NAME, VALID_PHONE, null, VALID_ORDER, VALID_ENDTIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidOrder_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_NAME, VALID_PHONE, VALID_ADDRESS, INVALID_ORDER, VALID_ENDTIME);
        String expectedMessage = Order.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullOrder_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_NAME, VALID_PHONE, VALID_ADDRESS, null, VALID_ENDTIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Order.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

}
