package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_METRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deliverycommand.DeliveryEditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.itemcommand.ItemEditCommand;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.Models;
import seedu.address.model.ModelsManager;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.predicate.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.item.Item;
import seedu.address.model.item.predicate.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditDeliveryDescriptorBuilder;
import seedu.address.testutil.EditItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //    INVENTORY ITEM
    public static final String VALID_NAME_CHICKEN = "Chicken";
    public static final String VALID_NAME_DUCK = "Duck";
    public static final String VALID_QUANTITY_CHICKEN = "11111111";
    public static final String VALID_QUANTITY_DUCK = "22222222";
    public static final String VALID_SUPPLIER_CHICKEN = "GIANT";
    public static final String VALID_SUPPLIER_DUCK = "Sheng Siong";
    public static final String VALID_TAG_DUCK = "meat";
    public static final String VALID_TAG_CHICKEN = "poultry";
    public static final String VALID_MAX_QUANTITY = "500";
    public static final String VALID_MAX_QUANTITY_CHICKEN = "50";
    public static final String VALID_MAX_QUANTITY_DUCK = "30";
    public static final String VALID_METRIC = "kg";

    public static final String NAME_DESC_CHICKEN = " " + PREFIX_NAME + VALID_NAME_CHICKEN;
    public static final String NAME_DESC_DUCK = " " + PREFIX_NAME + VALID_NAME_DUCK;
    public static final String QUANTITY_DESC_CHICKEN = " " + PREFIX_QUANTITY + VALID_QUANTITY_CHICKEN;
    public static final String QUANTITY_DESC_DUCK = " " + PREFIX_QUANTITY + VALID_QUANTITY_DUCK;
    public static final String SUPPLIER_DESC_CHICKEN = " " + PREFIX_SUPPLIER + VALID_SUPPLIER_CHICKEN;
    public static final String SUPPLIER_DESC_DUCK = " " + PREFIX_SUPPLIER + VALID_SUPPLIER_DUCK;
    public static final String TAG_DESC_CHICKEN = " " + PREFIX_TAG + VALID_TAG_CHICKEN;
    public static final String TAG_DESC_DUCK = " " + PREFIX_TAG + VALID_TAG_DUCK;
    public static final String MAX_QUANTITY_DESC = " " + PREFIX_MAX_QUANTITY + VALID_MAX_QUANTITY;
    public static final String MAX_QUANTITY_DESC_CHICKEN = " " + PREFIX_MAX_QUANTITY + VALID_MAX_QUANTITY_CHICKEN;
    public static final String MAX_QUANTITY_DESC_DUCK = " " + PREFIX_MAX_QUANTITY + VALID_MAX_QUANTITY_DUCK;
    public static final String METRIC_DESC_CHICKEN = " " + PREFIX_METRIC + VALID_METRIC;
    public static final String METRIC_DESC_DUCK = " " + PREFIX_METRIC + VALID_METRIC;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Salt&"; // '&' not allowed in names
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "911a"; // 'a' not allowed in quantity
    public static final String INVALID_SUPPLIER_DESC = " " + PREFIX_SUPPLIER; // empty string not allowed for suppliers
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "liquids*"; // '*' not allowed in tags
    public static final String INVALID_MAX_QUANTITY_DESC = " " + PREFIX_MAX_QUANTITY + "-30"; // neg num not allowed
    public static final String INVALID_METRIC = " " + PREFIX_METRIC + "kgs*"; // '*' not allowed in metric

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final ItemEditCommand.EditItemDescriptor DESC_CHICKEN;
    public static final ItemEditCommand.EditItemDescriptor DESC_DUCK;

    static {
        DESC_CHICKEN = new EditItemDescriptorBuilder().withName(VALID_NAME_CHICKEN)
                .withQuantity(VALID_QUANTITY_CHICKEN).withSupplier(VALID_SUPPLIER_CHICKEN)
                .withTags(VALID_TAG_CHICKEN).build();
        DESC_DUCK = new EditItemDescriptorBuilder().withName(VALID_NAME_DUCK)
                .withQuantity(VALID_QUANTITY_DUCK).withSupplier(VALID_SUPPLIER_DUCK)
                .withTags(VALID_TAG_DUCK, VALID_TAG_CHICKEN).build();
    }

    //    DELIVERY
    public static final String VALID_NAME_DAMITH = "DAMITH";
    public static final String VALID_NAME_AARON = "AARON";
    public static final String VALID_PHONE_DAMITH = "91231231";
    public static final String VALID_PHONE_AARON = "92323232";
    public static final String VALID_ADDRESS_DAMITH = "Jl Burong Kechil Tanjong Pajar No 92";
    public static final String VALID_ADDRESS_AARON = "Jl Koro koro kuru kuru Blk 251";
    public static final String VALID_ORDER_DAMITH = "Chicken rice 1x, not spicy";
    public static final String VALID_ORDER_AARON = "Iced Kopi x2, Prata plain x3";
    public static final String VALID_TIME_AARON = "0";
    public static final String VALID_TIME_DAMITH = "0";

    public static final String NAME_DESC_DAMITH = " " + PREFIX_NAME + VALID_NAME_DAMITH;
    public static final String NAME_DESC_AARON = " " + PREFIX_NAME + VALID_NAME_AARON;
    public static final String PHONE_DESC_DAMITH = " " + PREFIX_PHONE + VALID_PHONE_DAMITH;
    public static final String PHONE_DESC_AARON = " " + PREFIX_PHONE + VALID_PHONE_AARON;
    public static final String ADDRESS_DESC_DAMITH = " " + PREFIX_ADDRESS + VALID_ADDRESS_DAMITH;
    public static final String ADDRESS_DESC_AARON = " " + PREFIX_ADDRESS + VALID_ADDRESS_AARON;
    public static final String ORDER_DESC_DAMITH = " " + PREFIX_ORDER + VALID_ORDER_DAMITH;
    public static final String ORDER_DESC_AARON = " " + PREFIX_ORDER + VALID_ORDER_AARON;
    public static final String TIME_DESC_AARON = " " + PREFIX_TIME + VALID_TIME_AARON;
    public static final String TIME_DESC_DAMITH = " " + PREFIX_TIME + VALID_TIME_DAMITH;

    public static final String INVALID_DELIVERYNAME_DESC = " " + PREFIX_NAME + "Salt&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phone
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for address
    public static final String INVALID_ORDER_DESC = " " + PREFIX_ORDER; // empty string not allowed for orders
    public static final String INVALID_TIME = " " + PREFIX_TIME;

    public static final DeliveryEditCommand.EditDeliveryDescriptor DESC_AARON;
    public static final DeliveryEditCommand.EditDeliveryDescriptor DESC_DAMITH;

    static {
        DESC_AARON = new EditDeliveryDescriptorBuilder()
                .withName(VALID_NAME_AARON)
                .withPhone(VALID_PHONE_AARON)
                .withAddress(VALID_ADDRESS_AARON)
                .withOrder(VALID_ORDER_AARON)
                .build();
        DESC_DAMITH = new EditDeliveryDescriptorBuilder()
                .withName(VALID_NAME_DAMITH)
                .withPhone(VALID_PHONE_DAMITH)
                .withAddress(VALID_ADDRESS_DAMITH)
                .withOrder(VALID_ORDER_DAMITH)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, InventoryModel actualInventoryModel,
                                            CommandResult expectedCommandResult,
                                            InventoryModel expectedInventoryModel) {
        try {
            Models models = new ModelsManager(actualInventoryModel, new DeliveryModelManager());
            CommandResult result = command.execute(models);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedInventoryModel, actualInventoryModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, DeliveryModel actualDeliveryModel,
                                            CommandResult expectedCommandResult,
                                            DeliveryModel expectedDeliveryModel) {
        try {
            Models models = new ModelsManager(new InventoryModelManager(), actualDeliveryModel);
            CommandResult result = command.execute(models);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedDeliveryModel, actualDeliveryModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, InventoryModel, CommandResult, InventoryModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, InventoryModel actualInventoryModel,
                                            String expectedMessage,
                                            InventoryModel expectedInventoryModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualInventoryModel, expectedCommandResult, expectedInventoryModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, DeliveryModel, CommandResult, DeliveryModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, DeliveryModel actualDeliveryModel,
                                            String expectedMessage,
                                            DeliveryModel expectedDeliveryModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualDeliveryModel, expectedCommandResult, expectedDeliveryModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModels} matches {@code expectedModels}
     */
    public static void assertCommandSuccess(Command command, Models actualModels,
                                            CommandResult expectedCommandResult, Models expectedModels) {
        try {
            CommandResult result = command.execute(actualModels);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModels, actualModels);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Models, CommandResult, Models)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Models actualModels,
                                            String expectedMessage,
                                            Models expectedModels) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModels, expectedCommandResult, expectedModels);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the inventory book, filtered item list and selected item in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command,
                                            InventoryModel actualInventoryModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        InventoryBook expectedInventoryBook = new InventoryBook(actualInventoryModel.getInventoryBook());
        List<Item> expectedFilteredList = new ArrayList<>(actualInventoryModel.getFilteredAndSortedItemList());
        Models models = new ModelsManager(actualInventoryModel, new DeliveryModelManager());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(models));
        assertEquals(expectedInventoryBook, actualInventoryModel.getInventoryBook());
        assertEquals(expectedFilteredList, actualInventoryModel.getFilteredAndSortedItemList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the delivery book, filtered delivery list and selected delivery in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command,
                                            DeliveryModel actualDeliveryModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DeliveryBook expectedDeliveryBook = new DeliveryBook(actualDeliveryModel.getDeliveryBook());
        List<Delivery> expectedFilteredList = new ArrayList<>(actualDeliveryModel.getFilteredAndSortedDeliveryList());
        Models models = new ModelsManager(new InventoryModelManager(), actualDeliveryModel);

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(models));
        assertEquals(expectedDeliveryBook, actualDeliveryModel.getDeliveryBook());
        assertEquals(expectedFilteredList, actualDeliveryModel.getFilteredAndSortedDeliveryList());
    }

    /**
     * Updates {@code model}'s filtered and sorted list to show only the item at the given {@code targetIndex} in the
     * {@code model}'s inventory book.
     */
    public static void showItemAtIndex(InventoryModel inventoryModel, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < inventoryModel.getFilteredAndSortedItemList().size());

        Item item = inventoryModel.getFilteredAndSortedItemList().get(targetIndex.getZeroBased());
        final String[] splitName = item.getName().fullName.split("\\s+");
        inventoryModel.updateItemListFilter(
                new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, inventoryModel.getFilteredAndSortedItemList().size());
    }

    /**
     * Updates {@code model}'s filtered and sorted list to show only
     * the delivery at the given {@code targetIndex} in the {@code model}'s delivery book.
     */
    public static void showDeliveryAtIndex(DeliveryModel deliveryModel, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < deliveryModel.getFilteredAndSortedDeliveryList().size());

        Delivery delivery = deliveryModel.getFilteredAndSortedDeliveryList().get(targetIndex.getZeroBased());
        final String[] splitName = delivery.getName().fullName.split("\\s+");
        deliveryModel.updateFilteredDeliveryList(
                new DeliveryNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, deliveryModel.getFilteredAndSortedDeliveryList().size());
    }

}
