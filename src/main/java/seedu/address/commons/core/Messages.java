package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ITEM_DISPLAYED_INDEX = "The item index provided is invalid";
    public static final String MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX = "The delivery index provided is invalid";
    public static final String MESSAGE_UNDO_LIMIT_REACHED = "No more commands to undo";
    public static final String MESSAGE_REDO_LIMIT_REACHED = "No more commands to redo";
    public static final String MESSAGE_ITEMS_LISTED_OVERVIEW = "%1$d items listed!";
    public static final String MESSAGE_DELIVERIES_LISTED_OVERVIEW = "%1$d deliveries listed!";
    public static final String MESSAGE_EARLY_TEST_FAILURE = "Test should not have failed at this point!";
    public static final String MESSAGE_HELP_ON_START = "Press F1 to view the user guide,\n"
            + "Press F2 to get started with some commands!";
    public static final String HELP_START = getHelpStart();
    public static final String HELP_SUMMARY = getHelpSummary();
    public static final String DIVIDER = "\n======================================================================\n\n";

    public static final String getHelpStart() {
        StringBuilder summary = new StringBuilder();
        summary.append("Getting Started\n")
                .append("Welcome to OneShelf!\nYou have summoned me - your trusty Assistant, probably because you are "
                        + "lost and do not know where to start.\nBut don't worry, because that is the whole point of "
                        + "my existence!\n")
                .append(DIVIDER)
                .append("In this quest, we will go through feature by feature, so follow along with me!\n")
                .append("You should notice that the interface is divided into 2 main categories. "
                        + "The left part of the application is \n"
                        + "dedicated for inventory items slot whereas the right part is dedicated for "
                        + "storing pending deliveries.\n")
                .append(DIVIDER)
                .append("First, enter `clear-i` and `clear-d` to ensure that you do not have any existing "
                        + "pre-downloaded data. \n"
                        + "These commands are used to clear inventory items and pending deliveries respectively\n")
                .append("If there were existing items or deliveries beforehand, they should be cleared by now. "
                        + "If there were no pre-existing data, \n"
                        + "fret not! Your trusty Assistant will guide you how to add an inventory item and pending "
                        + "delivery in the next step \n"
                        + "of this journey. \n")
                .append(DIVIDER)
                .append("To add an Inventory Item, use the following command\n"
                        + "`add-i n/NAME q/QUANTITY [s/SUPPLIER] [max/MAX_QUANTITY] [metric/METRIC] [t/TAG]`\n"
                        + "The bracket connotation means that the attribute is optional. You will still be able "
                        + "to add an item without supplier\n"
                        + "or maximum quantity. Take note that for tags, you will be able to add in multiple tags "
                        + "to an inventory item.\n"
                        + "Example: `add-i n/CHICKEN q/50 s/NTUC t/meat` would mean that you want to add an "
                        + "inventory item of name CHICKEN\n"
                        + "of quantity 50 that was bought from NTUC store and tagged with meat category. "
                        + "If you want to add a metric of KG\n"
                        + "to the quantity, you can press KEY ARROW UP, and add metric/KG to it or whichever "
                        + "metric you deem suitable.\n")
                .append("Once you have added the metric, you should see that the item has a metric "
                        + "attribute attached to it.\n"
                        + "We are done with adding an inventory item! "
                        + "Adding a pending delivery is very similar to this,\n"
                        + "with just a slight difference in the command. You can enter the following "
                        + "command to add a delivery:\n"
                        + "`add-d n/NAME p/PHONE a/ADDRESS o/ORDER [by/TIME]`\n"
                        + "Example: `add-d n/DAMITH p/91231231 a/Jln Burong no 92 o/Mee Rebus by/15`\n")
                .append(DIVIDER)
                .append("GREAT JOB! Seems like you are getting the hang of it. If you have not seen the "
                        + "effect of clear-i and clear-d\n"
                        + "now is the time to do it! Afterwards, you may add the item and delivery above "
                        + "again just by scrolling up\n"
                        + "with the KEY ARROW UP button.\n")
                .append(DIVIDER)
                .append("If you want to decrease the quantity of an inventory item, you can use "
                        + "`remove-i INDEX q/QUANTITY`\n"
                        + "Example: `remove-i 1 30` would remove the quantity of item on first index by 30.\n"
                        + "Note that remove command is only available for inventory item as "
                        + "pending delivery does not have quantity.")
                .append(DIVIDER)
                .append("You may add a few more unique inventory items and pending deliveries if you would to do so.\n"
                        + "In the next step, we will learn about delete-i and delete-d\n"
                        + "Command for deleting an item is by using `delete-i INDEX`. An example would be like:\n"
                        + "`delete-i 1`\n"
                        + "After entering the following command, you should notice that the item at "
                        + "index 1 in the inventory book has\n"
                        + "now been deleted. To delete pending delivery, the difference in the "
                        + "command is by using `delete-d` instead\n"
                        + "of delete-i. Try it!\n")
                .append(DIVIDER)
                .append("You may now realize that it is very dangerous to delete an item as you "
                        + "may have mistaken or input the INDEX\n"
                        + "wrongly. Do not worry, as we have the `undo` command! If you were to "
                        + "try it now, you should notice that\n"
                        + "the deleted item or delivery should be back in your book again. On "
                        + "the side note, there is a `redo` feature!\n")
                .append(DIVIDER)
                .append("In case where your inventory and delivery book has grown larger in the "
                        + "future, you can find a particular\n"
                        + "item or delivery if you need to by using the command:\n"
                        + "`[n/NAME | s/SUPPLIER | t/TAG]`.\n"
                        + "Example: `find-i n/Chicken` returns any items containing the name "
                        + "chicken in inventory book.\n"
                        + "You may also put multiple keywords if you would like search a few items concurrently.\n"
                        + "Example: `find-d n/AARON p/91231231` returns any delivery that has name AARON and anyone\n"
                        + "who has the phone number 91231231.")
                .append(DIVIDER)
                .append("After entering the find feature, you can revert back to the entire list "
                        + "by entering `list-i` or `list-d`.\n")
                .append(DIVIDER)
                .append("CONGRATULATIONS! You have completed OneShelf's tutorial! If you would "
                        + "want to find out more about OneShelf,\n"
                        + "feel free to check out the user guide's link stated above!\n")
                .append(DIVIDER)
                .append("To close this window, press ALT + F4");
        return summary.toString();
    }

    public static final String getHelpSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Command Summary\n")
                .append(DIVIDER)
                .append("GENERAL COMMANDS\n")
                .append(DIVIDER)
                .append("Open up help options: help summary OR help start\n")
                .append("Undo previous command: undo\n")
                .append("Redo undone command: redo\n")
                .append("Exit application: exit\n")
                .append(DIVIDER)
                .append("INVENTORY SPECIFIC COMMANDS\n")
                .append(DIVIDER)
                .append("Add inventory: add-i n/NAME q/QUANTITY [s/SUPPLIER] [max/MAX_QUANTITY] [t/TAG] "
                        + "[metric/METRIC]\n")
                .append("Delete an inventory item: delete-i INDEX\n")
                .append("Edit an inventory item: edit-i INDEX [n/NAME] [q/QUANTITY]"
                        + " [s/SUPPLIER] [max/MAX_QUANTITY] [t/TAG] [metric/METRIC]\n")
                .append("Find an inventory item: find-i [n/NAME | s/SUPPLIER | "
                        + "t/TAG] [metric/METRIC]\n")
                .append("Clear all inventory items: clear-i\n")
                .append("List all inventory items: list-i\n")
                .append(DIVIDER)
                .append("DELIVERY SPECIFIC COMMANDS\n")
                .append(DIVIDER)
                .append("Add delivery: add-d n/NAME p/PHONE a/ADDRESS o/ORDER by/TIME\n")
                .append("Delete a delivery: delete-d INDEX\n")
                .append("Edit a delivery: edit-d INDEX [n/NAME] [p/PHONE] [a/ADDRESS] [o/ORDER] [by/TIME]\n")
                .append("Find a delivery: find-d [n/NAME | p/PHONE | a/ADDRESS | o/ORDER]\n")
                .append("Clear all deliveries: clear-d\n")
                .append("List all deliveries: list-d\n")
                .append(DIVIDER)
                .append("To close this window, press ALT + F4");
        return summary.toString();
    }
}
