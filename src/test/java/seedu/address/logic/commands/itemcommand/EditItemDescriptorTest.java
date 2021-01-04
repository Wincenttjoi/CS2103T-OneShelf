package seedu.address.logic.commands.itemcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUPPLIER_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DUCK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itemcommand.ItemEditCommand.EditItemDescriptor;
import seedu.address.testutil.EditItemDescriptorBuilder;

public class EditItemDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        ItemEditCommand.EditItemDescriptor descriptorWithSameValues = new EditItemDescriptor(DESC_CHICKEN);
        assertTrue(DESC_CHICKEN.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CHICKEN.equals(DESC_CHICKEN));

        // null -> returns false
        assertFalse(DESC_CHICKEN.equals(null));

        // different types -> returns false
        assertFalse(DESC_CHICKEN.equals(5));

        // different values -> returns false
        assertFalse(DESC_CHICKEN.equals(DESC_DUCK));

        // different name -> returns false
        EditItemDescriptor editedChicken = new EditItemDescriptorBuilder(DESC_CHICKEN)
                .withName(VALID_NAME_DUCK).build();
        assertFalse(DESC_CHICKEN.equals(editedChicken));

        // different quantity -> returns false
        editedChicken = new EditItemDescriptorBuilder(DESC_CHICKEN).withQuantity(VALID_QUANTITY_DUCK).build();
        assertFalse(DESC_CHICKEN.equals(editedChicken));

        // different supplier -> returns false
        editedChicken = new EditItemDescriptorBuilder(DESC_CHICKEN).withSupplier(VALID_SUPPLIER_DUCK).build();
        assertFalse(DESC_CHICKEN.equals(editedChicken));

        // different tags -> returns false
        editedChicken = new EditItemDescriptorBuilder(DESC_CHICKEN).withTags(VALID_TAG_DUCK).build();
        assertFalse(DESC_CHICKEN.equals(editedChicken));
    }
}
