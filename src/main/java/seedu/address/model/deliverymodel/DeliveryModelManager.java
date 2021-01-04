package seedu.address.model.deliverymodel;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_REDO_LIMIT_REACHED;
import static seedu.address.commons.core.Messages.MESSAGE_UNDO_LIMIT_REACHED;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;

public class DeliveryModelManager implements DeliveryModel {
    private static final Logger logger = LogsCenter.getLogger(DeliveryModelManager.class);

    private List<DeliveryBook> deliveryBookStateList = new ArrayList<>(MODEL_DEFAULT_STATES_LIMIT);
    private int deliveryBookStatePointer = -1;
    private int statesLimit = MODEL_DEFAULT_STATES_LIMIT;
    private final DeliveryBook deliveryBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Delivery> filteredDeliveries;

    /**
     * Initializes a DeliveryModelManager with the given deliveryBook and userPrefs.
     */
    public DeliveryModelManager(ReadOnlyDeliveryBook deliveryBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(deliveryBook, userPrefs);

        logger.fine("Initializing with delivery book: " + deliveryBook + " and user prefs " + userPrefs);

        this.deliveryBook = new DeliveryBook(deliveryBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredDeliveries = new FilteredList<>(this.deliveryBook.getDeliveryList());
    }

    public DeliveryModelManager() {
        this(new DeliveryBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getDeliveryBookFilePath() {
        return userPrefs.getDeliveryBookFilePath();
    }

    @Override
    public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
        requireAllNonNull(deliveryBookFilePath);
        userPrefs.setDeliveryBookFilePath(deliveryBookFilePath);
    }

    //=========== DeliveryBook ================================================================================

    @Override
    public void setDeliveryBook(ReadOnlyDeliveryBook deliveryBook) {
        this.deliveryBook.resetData(deliveryBook);
    }

    @Override
    public ReadOnlyDeliveryBook getDeliveryBook() {
        return deliveryBook;
    }

    @Override
    public void deleteDelivery(Delivery target) {
        deliveryBook.removeDelivery(target);
    }

    @Override
    public void addDelivery(Delivery delivery) {
        deliveryBook.addDelivery(delivery);
        updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
    }

    @Override
    public void setDelivery(Delivery target, Delivery editedDelivery) {
        requireAllNonNull(target, editedDelivery);
        deliveryBook.setDelivery(target, editedDelivery);
    }

    //=========== Filtered Delivery List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Delivery} backed by the internal list of
     * {@code versionedDeliveryBook}
     */
    @Override
    public ObservableList<Delivery> getFilteredAndSortedDeliveryList() {
        return new SortedList<>(filteredDeliveries, DELIVERY_COMPARATOR);
    }

    @Override
    public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
        requireNonNull(predicate);
        filteredDeliveries.setPredicate(predicate);
    }

    //=========== Redo/Undo ===============================================================================

    /**
     * Copies the current {@code InventoryBook} in the state list.
     */
    @Override
    public void commit() {
        assert deliveryBookStatePointer < deliveryBookStateList.size();
        assert deliveryBookStateList.size() <= statesLimit;

        if (canRedo()) {
            deliveryBookStateList = deliveryBookStateList.subList(0, deliveryBookStatePointer + 1);
        }
        if (deliveryBookStateIsFull()) {
            deliveryBookStateList.remove(0);
            deliveryBookStatePointer--;
        }
        deliveryBookStateList.add(new DeliveryBook(deliveryBook));
        deliveryBookStatePointer++;
    }

    /**
     * Shifts the current {@code InventoryBook} to the previous one in the state list.
     */
    @Override
    public void undo() throws UndoRedoLimitReachedException {
        if (canUndo()) {
            deliveryBookStatePointer--;
            deliveryBook.resetData(deliveryBookStateList.get(deliveryBookStatePointer));
        } else {
            throw new UndoRedoLimitReachedException(MESSAGE_UNDO_LIMIT_REACHED);
        }
    }

    /**
     * Shifts the current {@code InventoryBook} to the next one in the state list.
     */
    @Override
    public void redo() throws UndoRedoLimitReachedException {
        if (canRedo()) {
            deliveryBookStatePointer++;
            deliveryBook.resetData(deliveryBookStateList.get(deliveryBookStatePointer));
        } else {
            throw new UndoRedoLimitReachedException(MESSAGE_REDO_LIMIT_REACHED);
        }
    }

    @Override
    public void setStatesLimit(int limit) {
        statesLimit = limit;
    }

    private boolean canUndo() {
        return deliveryBookStatePointer > 0;
    }

    private boolean canRedo() {
        return deliveryBookStatePointer < deliveryBookStateList.size() - 1;
    }

    private boolean deliveryBookStateIsFull() {
        return deliveryBookStateList.size() >= statesLimit;
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DeliveryModelManager)) {
            return false;
        }

        // state check
        DeliveryModelManager other = (DeliveryModelManager) obj;
        return deliveryBook.equals(other.deliveryBook)
                && userPrefs.equals(other.userPrefs)
                && filteredDeliveries.equals(other.filteredDeliveries);
    }
}
