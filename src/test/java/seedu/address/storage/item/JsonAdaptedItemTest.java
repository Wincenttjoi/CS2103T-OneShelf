package seedu.address.storage.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.item.JsonAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.DUCK_WITH_MAX_QUANTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Metric;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Supplier;

public class JsonAdaptedItemTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_QUANTITY = "+6";
    private static final String INVALID_SUPPLIER = " ";
    private static final String INVALID_TAG = "#FooD";
    private static final String INVALID_MAX_QUANTITY = "-3";
    private static final String INVALID_METRIC = " ";

    private static final String VALID_NAME = DUCK_WITH_MAX_QUANTITY.getName().toString();
    private static final String VALID_QUANTITY = DUCK_WITH_MAX_QUANTITY.getQuantity().toString();
    private static final String VALID_SUPPLIER = DUCK_WITH_MAX_QUANTITY.getSupplier().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = DUCK_WITH_MAX_QUANTITY.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_MAX_QUANTITY = DUCK_WITH_MAX_QUANTITY.getMaxQuantity().get().toString();
    private static final String VALID_METRIC = DUCK_WITH_MAX_QUANTITY.getMetric().get().toString();

    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        JsonAdaptedItem item = new JsonAdaptedItem(DUCK_WITH_MAX_QUANTITY);
        assertEquals(DUCK_WITH_MAX_QUANTITY, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(INVALID_NAME, VALID_QUANTITY, VALID_SUPPLIER,
                VALID_TAGS, VALID_MAX_QUANTITY, VALID_METRIC);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(null, VALID_QUANTITY, VALID_SUPPLIER,
                VALID_TAGS, VALID_MAX_QUANTITY, VALID_METRIC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, INVALID_QUANTITY, VALID_SUPPLIER,
                VALID_TAGS, VALID_MAX_QUANTITY, VALID_METRIC);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, null, VALID_SUPPLIER,
                VALID_TAGS, VALID_MAX_QUANTITY, VALID_METRIC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidSupplier_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, VALID_QUANTITY, INVALID_SUPPLIER,
                VALID_TAGS, VALID_MAX_QUANTITY, VALID_METRIC);
        String expectedMessage = Supplier.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullSupplier_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, VALID_QUANTITY, null,
                VALID_TAGS, VALID_MAX_QUANTITY, VALID_METRIC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Supplier.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, VALID_QUANTITY, VALID_SUPPLIER,
                invalidTags, VALID_MAX_QUANTITY, VALID_METRIC);
        assertThrows(IllegalValueException.class, item::toModelType);
    }

    @Test
    public void toModelType_invalidMaxQuantity_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, VALID_QUANTITY, VALID_SUPPLIER,
                VALID_TAGS, INVALID_MAX_QUANTITY, VALID_METRIC);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidMetric_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, VALID_QUANTITY, VALID_SUPPLIER,
                VALID_TAGS, VALID_MAX_QUANTITY, INVALID_METRIC);
        String expectedMessage = Metric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }
}
