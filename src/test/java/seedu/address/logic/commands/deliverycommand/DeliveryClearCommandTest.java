package seedu.address.logic.commands.deliverycommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;

public class DeliveryClearCommandTest {

    @Test
    public void execute_emptyDeliveryBook_success() {
        DeliveryModel deliveryModel = new DeliveryModelManager();
        DeliveryModel expectedDeliveryModel = new DeliveryModelManager();

        assertCommandSuccess(new DeliveryClearCommand(), deliveryModel, DeliveryClearCommand.MESSAGE_SUCCESS,
                expectedDeliveryModel);
    }

    @Test
    public void execute_nonEmptyDeliveryBook_success() {
        DeliveryModel deliveryModel = new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
        DeliveryModel expectedDeliveryModel = new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
        expectedDeliveryModel.setDeliveryBook(new DeliveryBook());

        assertCommandSuccess(new DeliveryClearCommand(), deliveryModel, DeliveryClearCommand.MESSAGE_SUCCESS,
                expectedDeliveryModel);
    }
}
