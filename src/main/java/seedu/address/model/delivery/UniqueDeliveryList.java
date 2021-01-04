package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.delivery.exception.DeliveryNotFoundException;

/**
 * A list of deliveries that enforces uniqueness between its elements and does not allow nulls.
 * A delivery is considered unique by comparing using {@code Delivery#isSameItem(Delivery)}.
 * As such, adding and updating of deliveries uses Delivery#isSameDelivery(Delivery) for
 * equality so as to ensure that the delivery being added or updated is
 * unique in terms of identity in the UniqueDeliveryList.
 * However, the removal of a delivery uses Delivery#equals(Object) so
 * as to ensure that the delivery with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueDeliveryList implements Iterable<Delivery> {
    private final ObservableList<Delivery> internalList = FXCollections.observableArrayList();
    private final ObservableList<Delivery> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent delivery as the given argument.
     */
    public boolean contains(Delivery toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a delivery to the list.
     * The delivery must not already exist in the list.
     */
    public void add(Delivery toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the delivery {@code target} in the list with {@code editedDelivery}.
     * {@code target} must exist in the list.
     * The delivery identity of {@code editedDelivery} must not be the same as another existing delivery in the list.
     */
    public void setDelivery(Delivery target, Delivery editedDelivery) {
        requireAllNonNull(target, editedDelivery);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DeliveryNotFoundException();
        }

        internalList.set(index, editedDelivery);
    }

    /**
     * Removes the equivalent delivery from the list.
     * The delivery must exist in the list.
     */
    public void remove(Delivery toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DeliveryNotFoundException();
        }
    }

    public void setDeliveries(UniqueDeliveryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code deliveries}.
     * {@code deliveries} must not contain duplicate deliveries.
     */
    public void setDeliveries(List<Delivery> deliveries) {
        requireAllNonNull(deliveries);
        internalList.setAll(deliveries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Delivery> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Delivery> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDeliveryList // instanceof handles nulls
                && internalList.equals(((UniqueDeliveryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
