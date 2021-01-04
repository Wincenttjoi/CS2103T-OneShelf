package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_DAMITH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DAMITH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryBook;

/**
 * A utility class containing a list of {@code Delivery} objects to be used in tests.
 */
public class TypicalDeliveries {

    public static final Delivery KELVIN = new DeliveryBuilder().withName("Kelvin")
            .withPhone("91234332")
            .withAddress("Clementi Blk 235 #11-111")
            .withOrder("Char Kway Teow")
            .withTime("28 October 2020 00:00:00 000")
            .build();
    public static final Delivery MARCUS = new DeliveryBuilder().withName("Marcus")
            .withPhone("8198264")
            .withAddress("Jurong Blk 231 #15-123")
            .withOrder("Seafood Hor Fun x5")
            .withTime("28 October 2020 00:00:00 000")
            .build();

    // Manually added - Delivery's details found in {@code CommandTestUtil}
    public static final Delivery DAMITH_MANUAL = new DeliveryBuilder()
            .withName(VALID_NAME_DAMITH)
            .withPhone(VALID_PHONE_DAMITH)
            .withAddress(VALID_ADDRESS_DAMITH)
            .withOrder(VALID_ORDER_DAMITH)
            .withTime("28 October 2020 00:00:00 000")
            .build();
    public static final Delivery AARON_MANUAL = new DeliveryBuilder()
            .withName(VALID_NAME_AARON)
            .withPhone(VALID_PHONE_AARON)
            .withAddress(VALID_ADDRESS_AARON)
            .withOrder(VALID_ORDER_AARON)
            .withTime("28 October 2020 00:00:00 000")
            .build();

    private TypicalDeliveries() {} // prevents instantiation

    /**
     * Returns an {@code InventoryBook} with all the typical items.
     */
    public static DeliveryBook getTypicalDeliveryBook() {
        DeliveryBook deliveryBook = new DeliveryBook();
        for (Delivery delivery : getTypicalDeliveries()) {
            deliveryBook.addDelivery(delivery);
        }
        return deliveryBook;
    }

    public static List<Delivery> getTypicalDeliveries() {
        return new ArrayList<>(Arrays.asList(KELVIN, MARCUS));
    }
}

