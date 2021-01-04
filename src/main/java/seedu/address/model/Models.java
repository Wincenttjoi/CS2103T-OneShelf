package seedu.address.model;

import java.util.Map;

import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.inventorymodel.InventoryModel;

public interface Models {
    /**
     * Gets the {@code DeliveryModel} stored inside {@code Models}.
     *
     * @return The {@code DeliveryModel} stored inside {@code Models}.
     */
    DeliveryModel getDeliveryModel();

    /**
     * Sets the {@code DeliveryModel} stored inside {@code Models}.
     */
    void setDeliveryModel(DeliveryModel deliveryModel);

    /**
     * Gets the {@code InventoryModel} stored inside {@code Models}.
     *
     * @return The {@code InventoryModel} stored inside {@code Models}.
     */
    InventoryModel getInventoryModel();

    /**
     * Sets the {@code InventoryModel} stored inside {@code Models}.
     */
    void setInventoryModel(InventoryModel inventoryModel);

    /**
     * Gets every {@code Model} as a {@code Map}.
     *
     * @return a {@code Map} mapping each {@code Model}'s key to its respective {@code Model}.
     */
    Map<? extends String, ? extends Model> getModelsAsMap();

    /**
     * Sets the maximum number of states that all the models should keep
     */
    void setStatesLimit(int limit);

    /**
     * Commits the current state of {@code Models} into history.
     */
    void commit();

    /**
     * Reverts the current state of {@code Models} one step in history.
     */
    void undo() throws UndoRedoLimitReachedException;

    /**
     * Redoes an undone state of {@code Models} one step forward in history.
     */
    void redo() throws UndoRedoLimitReachedException;
}
