package seedu.address.logic.commands.deliverycommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeliveryListCommand.
 */
public class DeliveryListCommandTest {

    private DeliveryModel deliveryModel;
    private DeliveryModel expectedDeliveryModel;

    @BeforeEach
    public void setUp() {
        deliveryModel = new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
        expectedDeliveryModel = new DeliveryModelManager(deliveryModel.getDeliveryBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new DeliveryListCommand(), deliveryModel, DeliveryListCommand.MESSAGE_SUCCESS,
                expectedDeliveryModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        assertCommandSuccess(new DeliveryListCommand(), deliveryModel, DeliveryListCommand.MESSAGE_SUCCESS,
                expectedDeliveryModel);
    }
}
