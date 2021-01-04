package seedu.address.logic.commands.deliverycommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.testutil.DeliveryBuilder;

public class DeliveryAddCommandIntegrationTest {

    private DeliveryModel model;

    @BeforeEach
    public void setUp() {
        model = new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
    }

    @Test
    public void execute_newDelivery_success() {
        Delivery validDelivery = new DeliveryBuilder().build();

        DeliveryModel expectedModel = new DeliveryModelManager(model.getDeliveryBook(), new UserPrefs());
        expectedModel.addDelivery(validDelivery);

        assertCommandSuccess(new DeliveryAddCommand(validDelivery), model,
                String.format(DeliveryAddCommand.MESSAGE_SUCCESS, validDelivery), expectedModel);
    }
}
