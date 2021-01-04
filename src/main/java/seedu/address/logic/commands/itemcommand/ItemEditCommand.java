package seedu.address.logic.commands.itemcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_METRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.inventorymodel.InventoryModel.PREDICATE_SHOW_ALL_ITEMS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.item.Item;
import seedu.address.model.item.Metric;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;
import seedu.address.model.item.Tag;

/**
 * Edits the details of an existing item in the inventory book.
 */
public class ItemEditCommand extends ItemCommand {

    public static final String COMMAND_WORD = "edit-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item identified "
            + "by the index number used in the displayed item list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "ITEM_NAME | "
            + PREFIX_QUANTITY + "QUANTITY | "
            + PREFIX_SUPPLIER + "SUPPLIER | "
            + PREFIX_TAG + "TAG |"
            + PREFIX_MAX_QUANTITY + "MAX_QUANTITY | "
            + PREFIX_METRIC + "METRIC]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "21 ";

    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited Item: \n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the inventory book.";

    private final Index index;
    private final EditItemDescriptor editItemDescriptor;

    /**
     * @param index of the item in the filtered item list to edit
     * @param editItemDescriptor details to edit the item with
     */
    public ItemEditCommand(Index index, EditItemDescriptor editItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editItemDescriptor);

        this.index = index;
        this.editItemDescriptor = new EditItemDescriptor(editItemDescriptor);
    }

    @Override
    public CommandResult execute(Models models) throws CommandException {
        requireNonNull(models);
        requireNonNull(models.getInventoryModel());
        InventoryModel inventoryModel = models.getInventoryModel();
        List<Item> lastShownList = inventoryModel.getFilteredAndSortedItemList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(itemToEdit, editItemDescriptor);

        if (!itemToEdit.isSameItem(editedItem) && inventoryModel.hasItem(editedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        inventoryModel.setItem(itemToEdit, editedItem);
        inventoryModel.updateItemListFilter(PREDICATE_SHOW_ALL_ITEMS);
        models.commit();

        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code editItemDescriptor}.
     */
    private static Item createEditedItem(Item itemToEdit, EditItemDescriptor editItemDescriptor) {
        assert itemToEdit != null;

        Name updatedName = editItemDescriptor.getName().orElse(itemToEdit.getName());
        Quantity updatedQuantity = editItemDescriptor.getQuantity().orElse(itemToEdit.getQuantity());
        Supplier updatedSupplier = editItemDescriptor.getSupplier().orElse(itemToEdit.getSupplier());
        Set<Tag> updatedTags = editItemDescriptor.getTags().orElse(itemToEdit.getTags());
        Quantity updatedMaxQuantity = editItemDescriptor.getMaxQuantity()
                .or(() -> itemToEdit.getMaxQuantity())
                .orElse(null);
        Metric metric = editItemDescriptor.getMetric()
                .or(() -> itemToEdit.getMetric())
                .orElse(null);


        return new Item(updatedName, updatedQuantity, updatedSupplier, updatedTags, updatedMaxQuantity, metric);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemEditCommand)) {
            return false;
        }

        // state check
        ItemEditCommand e = (ItemEditCommand) other;
        return index.equals(e.index)
                && editItemDescriptor.equals(e.editItemDescriptor);
    }

    /**
     * Stores the details to edit the item with. Each non-empty field value will replace the
     * corresponding field value of the item.
     */
    public static class EditItemDescriptor {
        private Name name;
        private Quantity quantity;
        private Supplier supplier;
        private Set<Tag> tags;
        private Quantity maxQuantity;
        private Metric metric;

        public EditItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditItemDescriptor(EditItemDescriptor toCopy) {
            setName(toCopy.name);
            setQuantity(toCopy.quantity);
            setSupplier(toCopy.supplier);
            setTags(toCopy.tags);
            setMaxQuantity(toCopy.maxQuantity);
            setMetric(toCopy.metric);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, quantity, supplier, tags, maxQuantity, metric);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setSupplier(Supplier supplier) {
            this.supplier = supplier;
        }

        public Optional<Supplier> getSupplier() {
            return Optional.ofNullable(supplier);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setMaxQuantity(Quantity maxQuantity) {
            this.maxQuantity = maxQuantity;
        }

        public Optional<Quantity> getMaxQuantity() {
            return Optional.ofNullable(maxQuantity);
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
        }

        public Optional<Metric> getMetric() {
            return Optional.ofNullable(metric);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditItemDescriptor)) {
                return false;
            }

            // state check
            EditItemDescriptor e = (EditItemDescriptor) other;

            return getName().equals(e.getName())
                    && getQuantity().equals(e.getQuantity())
                    && getSupplier().equals(e.getSupplier())
                    && getTags().equals(e.getTags())
                    && getMaxQuantity().equals(e.getMaxQuantity())
                    && getMetric().equals(e.getMetric());
        }
    }
}
