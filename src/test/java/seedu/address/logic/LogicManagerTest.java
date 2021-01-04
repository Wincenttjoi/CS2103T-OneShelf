package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.MAX_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.METRIC_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.SUPPLIER_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_QUANTITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_METRIC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.CHICKEN_MANUAL;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.itemcommand.ItemAddCommand;
import seedu.address.logic.commands.itemcommand.ItemListCommand;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;
import seedu.address.model.inventorymodel.ReadOnlyInventoryBook;
import seedu.address.model.item.Item;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.delivery.JsonDeliveryBookStorage;
import seedu.address.storage.item.JsonInventoryBookStorage;
import seedu.address.testutil.ItemBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private InventoryModel inventoryModel = new InventoryModelManager();
    private DeliveryModel deliveryModel = new DeliveryModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonInventoryBookStorage inventoryBookStorage =
                new JsonInventoryBookStorage(temporaryFolder.resolve("inventoryBook.json"));
        JsonDeliveryBookStorage deliveryBookStorage =
                new JsonDeliveryBookStorage(temporaryFolder.resolve("deliveryBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(inventoryBookStorage, deliveryBookStorage, userPrefsStorage);
        logic = new LogicManager(inventoryModel, deliveryModel, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete-i 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ItemListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ItemListCommand.MESSAGE_SUCCESS, inventoryModel);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonInventoryBookIoExceptionThrowingStub
        JsonInventoryBookStorage inventoryBookStorage =
            new JsonInventoryBookIoExceptionThrowingStub(
                temporaryFolder.resolve("ioExceptionInventoryBook.json"));
        JsonDeliveryBookStorage deliveryBookStorage =
                new JsonDeliveryBookStorage(temporaryFolder.resolve("deliveryBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(inventoryBookStorage, deliveryBookStorage, userPrefsStorage);
        logic = new LogicManager(inventoryModel, deliveryModel, storage);

        // Execute add command
        String addCommand = ItemAddCommand.COMMAND_WORD + NAME_DESC_CHICKEN + QUANTITY_DESC_CHICKEN
                + SUPPLIER_DESC_CHICKEN + MAX_QUANTITY_DESC + METRIC_DESC_CHICKEN;
        Item expectedItem = new ItemBuilder(CHICKEN_MANUAL)
                .withTags()
                .withMaxQuantity(VALID_MAX_QUANTITY)
                .withMetric(VALID_METRIC).build();
        InventoryModelManager expectedModel = new InventoryModelManager();
        expectedModel.addItem(expectedItem);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredAndSortedItemList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, InventoryModel)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            InventoryModel expectedInventoryModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedInventoryModel, inventoryModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, InventoryModel)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, InventoryModel)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, InventoryModel)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        InventoryModel expectedInventoryModel =
                new InventoryModelManager(inventoryModel.getInventoryBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedInventoryModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, InventoryModel)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, InventoryModel expectedInventoryModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedInventoryModel, inventoryModel);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonInventoryBookIoExceptionThrowingStub extends JsonInventoryBookStorage {
        private JsonInventoryBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveInventoryBook(ReadOnlyInventoryBook inventoryBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
