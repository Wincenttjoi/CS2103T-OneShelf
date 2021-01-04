package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path inventoryBookFilePath = Paths.get("data" , "inventorybook.json");
    private Path deliveryBookFilePath = Paths.get("data", "deliverybook.json");

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
        setGuiSettings(newUserPrefs.getGuiSettings());
        setInventoryBookFilePath(newUserPrefs.getInventoryBookFilePath());
        setDeliveryBookFilePath(newUserPrefs.getDeliveryBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getInventoryBookFilePath() {
        return inventoryBookFilePath;
    }

    public void setInventoryBookFilePath(Path inventoryBookFilePath) {
        requireNonNull(inventoryBookFilePath);
        this.inventoryBookFilePath = inventoryBookFilePath;
    }

    public Path getDeliveryBookFilePath() {
        return deliveryBookFilePath;
    }

    public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
        requireNonNull(deliveryBookFilePath);
        this.deliveryBookFilePath = deliveryBookFilePath;
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

        return guiSettings.equals(o.guiSettings)
                && inventoryBookFilePath.equals(o.inventoryBookFilePath)
                && deliveryBookFilePath.equals(o.deliveryBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, inventoryBookFilePath, deliveryBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nInventory data file location : " + inventoryBookFilePath);
        sb.append("\nDelivery data file location : " + deliveryBookFilePath);
        return sb.toString();
    }

}
