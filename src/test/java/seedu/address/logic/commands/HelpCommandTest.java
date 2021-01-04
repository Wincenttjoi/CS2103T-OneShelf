package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.help.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.help.HelpStartCommand;
import seedu.address.logic.commands.help.HelpSummaryCommand;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.inventorymodel.InventoryModel;
import seedu.address.model.inventorymodel.InventoryModelManager;

public class HelpCommandTest {
    private InventoryModel inventoryModel = new InventoryModelManager();
    private InventoryModel expectedInventoryModel = new InventoryModelManager();

    @Test
    public void execute_helpStart_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false, false);
        assertCommandSuccess(new HelpStartCommand(), inventoryModel, expectedCommandResult, expectedInventoryModel);
    }

    @Test
    public void execute_helpSummary_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, false, true, false);
        assertCommandSuccess(new HelpSummaryCommand(), inventoryModel, expectedCommandResult, expectedInventoryModel);
    }
}
