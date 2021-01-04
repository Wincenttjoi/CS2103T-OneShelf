package seedu.address.model;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;

/**
 * Class that implements {@code Models} interface. Serves as a wrapper class for all models.
 */
public class ModelsManager implements Models {

    public static final String DELIVERY_MODEL_KEY = "Delivery Model";
    public static final String INVENTORY_MODEL_KEY = "Inventory Model";

    private DeliveryModel deliveryModel;
    private InventoryModel inventoryModel;
    private Map<String, Model> modelMap;

    /**
     * Initialise a {@code ModelManager} to hold empty models
     */
    public ModelsManager() {
        this(new InventoryModelManager(), new DeliveryModelManager());
    }

    /**
     * Initialise a {@code ModelManager} to hold the specificied models
     * @param inventoryModel {@InventoryModel} which the {@ModelManager} stores
     * @param deliveryModel {@DeliveryModel} which the {@ModelManager} stores
     */
    public ModelsManager(InventoryModel inventoryModel, DeliveryModel deliveryModel) {
        this.deliveryModel = deliveryModel;
        this.inventoryModel = inventoryModel;
        modelMap = initialiseModelMap();
        commit();
    }

    /**
     * Sets the {@code DeliveryModel} stored inside {@code Models}.
     */
    @Override
    public void setDeliveryModel(DeliveryModel deliveryModel) {
        this.deliveryModel = deliveryModel;
        modelMap.put(DELIVERY_MODEL_KEY, deliveryModel);
    }

    /**
     * Sets the {@code InventoryModel} stored inside {@code Models}.
     */
    @Override
    public void setInventoryModel(InventoryModel inventoryModel) {
        this.inventoryModel = inventoryModel;
        modelMap.put(INVENTORY_MODEL_KEY, inventoryModel);
    }

    /**
     * Gets the {@code DeliveryModel} stored inside {@code Models}.
     *
     * @return The {@code DeliveryModel} stored inside {@code Models}.
     */
    @Override
    public DeliveryModel getDeliveryModel() {
        return deliveryModel;
    }

    /**
     * Gets the {@code InventoryModel} stored inside {@code Models}.
     *
     * @return The {@code InventoryModel} stored inside {@code Models}.
     */
    @Override
    public InventoryModel getInventoryModel() {
        return inventoryModel;
    }

    /**
     * Gets every {@code Model} as a {@code Map}.
     *
     * @return a {@code Map} mapping each {@code Model}'s key to its respective {@code Model}.
     */
    @Override
    public Map<? extends String, ? extends Model> getModelsAsMap() {
        return modelMap;
    }

    /**
     * Sets the maximum number of states that a model should keep
     */
    @Override
    public void setStatesLimit(int limit) {
        assert limit >= 1;

        for (Model model: modelMap.values()) {
            model.setStatesLimit(limit);
        }
    }

    /**
     * Commits the current state of {@code Models} into history.
     */
    @Override
    public void commit() {
        for (Model model : modelMap.values()) {
            model.commit();
        }
    }

    /**
     * Reverts the current state of {@code Models} one step in history.
     */
    @Override
    public void undo() throws UndoRedoLimitReachedException {
        for (Model model : modelMap.values()) {
            model.undo();
        }
    }

    /**
     * Redoes an undone state of {@code Models} one step forward in history.
     */
    @Override
    public void redo() throws UndoRedoLimitReachedException {
        for (Model model : modelMap.values()) {
            model.redo();
        }
    }

    private Map<String, Model> initialiseModelMap() {
        Map<String, Model> tempMap = new HashMap<>();
        tempMap.put(DELIVERY_MODEL_KEY, deliveryModel);
        tempMap.put(INVENTORY_MODEL_KEY, inventoryModel);
        return tempMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ModelsManager)) {
            return false;
        } else {
            DeliveryModel objDeliveryModel = ((ModelsManager) obj).getDeliveryModel();
            InventoryModel objInventoryModel = ((ModelsManager) obj).getInventoryModel();
            return objDeliveryModel.equals(this.deliveryModel) && objInventoryModel.equals(this.inventoryModel);
        }
    }
}
