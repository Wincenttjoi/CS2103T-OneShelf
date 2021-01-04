package seedu.address.storage.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.deliverymodel.DeliveryBook;
import seedu.address.testutil.TypicalDeliveries;

public class JsonSerializableDeliveryBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDeliveryBookTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalDeliveryDeliveryBook.json");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidDeliveryDeliveryBook.json");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateDeliveryDeliveryBook.json");


    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableDeliveryBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ITEMS_FILE,
                JsonSerializableDeliveryBook.class).get();
        DeliveryBook inventoryBookFromFile = dataFromFile.toModelType();
        DeliveryBook typicalPersonsDeliveryBook = TypicalDeliveries.getTypicalDeliveryBook();
        assertEquals(inventoryBookFromFile, typicalPersonsDeliveryBook);
    }

    @Test
    public void toModelType_invalidDeliveryFile_throwsIllegalValueException() throws Exception {
        JsonSerializableDeliveryBook dataFromFile = JsonUtil.readJsonFile(INVALID_ITEM_FILE,
                JsonSerializableDeliveryBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
