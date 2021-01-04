package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.item.Item;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the item in the {@code model}'s item list.
     */
    public static Index getMidIndex(InventoryModel inventoryModel) {
        return Index.fromOneBased(inventoryModel.getFilteredAndSortedItemList().size() / 2);
    }

    /**
     * Returns the last index of the item in the {@code model}'s item list.
     */
    public static Index getLastIndex(InventoryModel inventoryModel) {
        return Index.fromOneBased(inventoryModel.getFilteredAndSortedItemList().size());
    }

    /**
     * Returns the item in the {@code model}'s item list at {@code index}.
     */
    public static Item getItem(InventoryModel inventoryModel, Index index) {
        return inventoryModel.getFilteredAndSortedItemList().get(index.getZeroBased());
    }
}
