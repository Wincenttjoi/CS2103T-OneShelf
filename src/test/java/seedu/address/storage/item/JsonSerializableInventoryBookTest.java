package seedu.address.storage.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.inventorymodel.InventoryBook;
import seedu.address.testutil.TypicalItems;

public class JsonSerializableInventoryBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableInventoryBookTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemInventoryBook.json");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemInventoryBook.json");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemInventoryBook.json");


    @Test
    public void toModelType_typicalItemsFile_success() throws Exception {
        JsonSerializableInventoryBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ITEMS_FILE,
                JsonSerializableInventoryBook.class).get();
        InventoryBook inventoryBookFromFile = dataFromFile.toModelType();
        InventoryBook typicalPersonsInventoryBook = TypicalItems.getTypicalInventoryBook();
        assertEquals(inventoryBookFromFile, typicalPersonsInventoryBook);
    }

    @Test
    public void toModelType_invalidItemFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInventoryBook dataFromFile = JsonUtil.readJsonFile(INVALID_ITEM_FILE,
                JsonSerializableInventoryBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }


    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableInventoryBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ITEM_FILE,
                JsonSerializableInventoryBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableInventoryBook.MESSAGE_DUPLICATE_ITEM,
                dataFromFile::toModelType);
    }


}
