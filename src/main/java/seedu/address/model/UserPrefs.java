package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private Path inventoryBookFilePath = Paths.get("data" , "inventorybook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setInventoryBookFilePath(newUserPrefs.getInventoryBookFilePath());
    }

    public Path getInventoryBookFilePath() {
        return inventoryBookFilePath;
    }

    public void setInventoryBookFilePath(Path inventoryBookFilePath) {
        requireNonNull(inventoryBookFilePath);
        this.inventoryBookFilePath = inventoryBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return inventoryBookFilePath.equals(o.inventoryBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Local data file location : " + inventoryBookFilePath);
        return sb.toString();
    }

}
