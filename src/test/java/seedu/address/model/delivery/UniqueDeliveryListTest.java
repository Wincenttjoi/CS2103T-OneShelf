package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_AARON;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.KELVIN;
import static seedu.address.testutil.TypicalDeliveries.MARCUS;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.delivery.exception.DeliveryNotFoundException;
import seedu.address.testutil.DeliveryBuilder;

public class UniqueDeliveryListTest {

    private final UniqueDeliveryList uniqueDeliveryList = new UniqueDeliveryList();

    @Test
    public void contains_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.contains(null));
    }

    @Test
    public void contains_deliveryNotInList_returnsFalse() {
        assertFalse(uniqueDeliveryList.contains(KELVIN));
    }

    @Test
    public void contains_deliveryInList_returnsTrue() {
        uniqueDeliveryList.add(KELVIN);
        assertTrue(uniqueDeliveryList.contains(KELVIN));
    }

    @Test
    public void contains_deliveryWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDeliveryList.add(KELVIN);
        Delivery editedCopy = new DeliveryBuilder(KELVIN)
            .withOrder(VALID_ORDER_AARON)
            .build();
        assertFalse(uniqueDeliveryList.contains(editedCopy));
    }

    @Test
    public void add_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.add(null));
    }

    @Test
    public void setDelivery_nullTargetDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(null, KELVIN));
    }

    @Test
    public void setDelivery_nullEditedDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(KELVIN, null));
    }

    @Test
    public void setDelivery_targetDeliveryNotInList_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> uniqueDeliveryList.setDelivery(KELVIN, KELVIN));
    }

    @Test
    public void setDelivery_editedDeliveryIsSameDelivery_success() {
        uniqueDeliveryList.add(KELVIN);
        uniqueDeliveryList.setDelivery(KELVIN, KELVIN);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(KELVIN);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasSameIdentity_success() {
        uniqueDeliveryList.add(KELVIN);
        Delivery editedKelvin = new DeliveryBuilder(KELVIN).withOrder(VALID_ORDER_AARON)
                .build();
        uniqueDeliveryList.setDelivery(KELVIN, editedKelvin);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(editedKelvin);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void remove_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.remove(null));
    }

    @Test
    public void remove_deliveryDoesNotExist_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> uniqueDeliveryList.remove(KELVIN));
    }

    @Test
    public void remove_existingDelivery_removesDelivery() {
        uniqueDeliveryList.add(KELVIN);
        uniqueDeliveryList.remove(KELVIN);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_nullUniqueDeliveryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(null, null));
    }

    @Test
    public void setDelivery_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(null, null));
    }

    @Test
    public void setDeliveries_list_replacesOwnListWithProvidedList() {
        uniqueDeliveryList.add(KELVIN);
        List<Delivery> deliveryList = Collections.singletonList(MARCUS);
        uniqueDeliveryList.setDeliveries(deliveryList);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(MARCUS);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDeliveryList.asUnmodifiableObservableList().remove(0));
    }
}
