package seedu.address.logic.commands.deliverycommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.delivery.Address;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.Order;
import seedu.address.model.delivery.Phone;
import seedu.address.model.delivery.Time;
import seedu.address.model.deliverymodel.DeliveryModel;

/**
 * Edits the details of an existing delivery in the delivery book.
 */
public class DeliveryEditCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = "edit-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the delivery identified "
            + "by the index number used in the displayed delivery list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "ITEM_NAME | "
            + PREFIX_PHONE + "PHONE | "
            + PREFIX_ADDRESS + "ADDRESS | "
            + PREFIX_ORDER + "ORDER | "
            + PREFIX_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91111111 ";

    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited Delivery: \n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditDeliveryDescriptor editDeliveryDescriptor;

    /**
     * @param index of the item in the filtered item list to edit
     * @param editDeliveryDescriptor details to edit the item with
     */
    public DeliveryEditCommand(Index index, EditDeliveryDescriptor editDeliveryDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeliveryDescriptor);

        this.index = index;
        this.editDeliveryDescriptor = new EditDeliveryDescriptor(editDeliveryDescriptor);
    }

    @Override
    public CommandResult execute(Models models) throws CommandException {
        requireNonNull(models);
        requireNonNull(models.getDeliveryModel());
        DeliveryModel deliveryModel = models.getDeliveryModel();
        List<Delivery> lastShownList = deliveryModel.getFilteredAndSortedDeliveryList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        Delivery deliveryToEdit = lastShownList.get(index.getZeroBased());
        Delivery editedDelivery = createEditedDelivery(deliveryToEdit, editDeliveryDescriptor);

        deliveryModel.setDelivery(deliveryToEdit, editedDelivery);
        deliveryModel.updateFilteredDeliveryList(DeliveryModel.PREDICATE_SHOW_ALL_DELIVERIES);
        models.commit();

        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedDelivery));
    }

    /**
     * Creates and returns a {@code Delivery} with the details of {@code deliveryToEdit}
     * edited with {@code editDeliveryDescriptor}.
     */
    private static Delivery createEditedDelivery(Delivery deliveryToEdit,
                                                 EditDeliveryDescriptor editDeliveryDescriptor) {
        assert deliveryToEdit != null;

        DeliveryName updatedName = editDeliveryDescriptor.getDeliveryName().orElse(deliveryToEdit.getName());
        Phone updatedPhone = editDeliveryDescriptor.getPhone().orElse(deliveryToEdit.getPhone());
        Address updatedAddress = editDeliveryDescriptor.getAddress().orElse(deliveryToEdit.getAddress());
        Order updatedOrder = editDeliveryDescriptor.getOrder().orElse(deliveryToEdit.getOrder());
        Time updatedTime = editDeliveryDescriptor.getTime().orElse(deliveryToEdit.getTime());

        return new Delivery(updatedName, updatedPhone, updatedAddress, updatedOrder, updatedTime);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryEditCommand)) {
            return false;
        }

        // state check
        DeliveryEditCommand e = (DeliveryEditCommand) other;
        return index.equals(e.index)
                && editDeliveryDescriptor.equals(e.editDeliveryDescriptor);
    }

    /**
     * Stores the details to edit the delivery with. Each non-empty field value will replace the
     * corresponding field value of the delivery.
     */
    public static class EditDeliveryDescriptor {
        private DeliveryName deliveryName;
        private Phone phone;
        private Address address;
        private Order order;
        private Time time;

        public EditDeliveryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code order} is used internally.
         */
        public EditDeliveryDescriptor(EditDeliveryDescriptor toCopy) {
            setDeliveryName(toCopy.deliveryName);
            setPhone(toCopy.phone);
            setAddress(toCopy.address);
            setOrder(toCopy.order);
            setTime(toCopy.time);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(deliveryName, phone, address, order, time);
        }

        public void setDeliveryName(DeliveryName deliveryName) {
            this.deliveryName = deliveryName;
        }

        public Optional<DeliveryName> getDeliveryName() {
            return Optional.ofNullable(deliveryName);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public Optional<Order> getOrder() {
            return Optional.ofNullable(order);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDeliveryDescriptor)) {
                return false;
            }

            // state check
            EditDeliveryDescriptor e = (EditDeliveryDescriptor) other;

            return getDeliveryName().equals(e.getDeliveryName())
                    && getPhone().equals(e.getPhone())
                    && getAddress().equals(e.getAddress())
                    && getOrder().equals(e.getOrder());
        }
    }
}
