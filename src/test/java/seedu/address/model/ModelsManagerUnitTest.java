package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalItems.getTypicalInventoryBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;

class ModelsManagerUnitTest {

    @Test
    void setStatesLimit_statesLimitIs0_assertionError() {
        Models models = new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel());
        assertThrows(AssertionError.class, () -> {
            models.setStatesLimit(0);
            models.commit();
        });
    }

    @Test
    void equals() {
        assertEquals(new ModelsManager(), new ModelsManager());

        assertEquals(new ModelsManager(makeTestInventoryModel(), new DeliveryModelManager()),
                new ModelsManager(makeTestInventoryModel(), new DeliveryModelManager()));

        assertEquals(new ModelsManager(new InventoryModelManager(), makeTestDeliveryModel()),
                new ModelsManager(new InventoryModelManager(), makeTestDeliveryModel()));

        assertEquals(new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel()),
                new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel()));

        InventoryModel inventoryModel = makeTestInventoryModel();
        DeliveryModel deliveryModel = makeTestDeliveryModel();
        assertEquals(new ModelsManager(inventoryModel, deliveryModel),
                new ModelsManager(inventoryModel, deliveryModel));

        assertNotEquals(new Object(), new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel()));

        assertNotEquals(new ModelsManager(makeTestInventoryModel(), makeTestDeliveryModel()),
                new ModelsManager(new InventoryModelManager(), new DeliveryModelManager()));
    }

    private InventoryModel makeTestInventoryModel() {
        return new InventoryModelManager(getTypicalInventoryBook(), new UserPrefs());
    }

    private DeliveryModel makeTestDeliveryModel() {
        return new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
    }
}
