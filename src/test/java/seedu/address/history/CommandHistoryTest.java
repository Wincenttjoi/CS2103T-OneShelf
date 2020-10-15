package seedu.address.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.history.exception.HistoryException;

public class CommandHistoryTest {

    @Test
    public void constructorInvalidLengthLimit_throwsHistoryException() {
        // instantiating with a negative limit -> throws HistoryException
        assertThrows(HistoryException.class, () -> new CommandHistory(-1));

        // instantiating with a limit of 0 -> throws HistoryException
        assertThrows(HistoryException.class, () -> new CommandHistory(0));
    }

    @Test
    public void nextCommandThenPreviousCommandThenNextCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalFirstCommand = Optional.of(firstCommand);
        Optional<String> optionalSecondCommand = Optional.of(secondCommand);

        CommandHistory cOne = new CommandHistory(1);
        CommandHistory cTwo = new CommandHistory(2);
        CommandHistory cThree = new CommandHistory(3);

        //========================= PRIOR TO ADDING =========================
        // cOne = {};
        // cOne Current index = -1;
        //
        // cTwo = {};
        // cTwo Current index = -1;
        //
        // cThree = {};
        // cThree Current index = -1;
        //===================================================================

        // Adding firstCommand
        cOne.addToHistory(firstCommand);
        cTwo.addToHistory(firstCommand);
        cThree.addToHistory(firstCommand);

        // Adding secondCommand
        cOne.addToHistory(secondCommand);
        cTwo.addToHistory(secondCommand);
        cThree.addToHistory(secondCommand);

        //================== AFTER ADDING TO HISTORY ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //==============================================================

        // cOne has limit of 1, thus there is no nextCommand
        assertEquals(Optional.empty(), cOne.nextCommand());

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cTwo.nextCommand());

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cThree.nextCommand());

        //================== AFTER 1x NEXT COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===========================================================

        // cOne has limit of 1, thus there is no previousCommand
        assertEquals(Optional.empty(), cOne.previousCommand());

        // previous command of cTwo should be firstCommand
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // previous command of cThree should be firstCommand
        assertEquals(optionalFirstCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 0;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 0;
        //===============================================================

        // cOne has limit of 1, thus there is no previousCommand
        assertEquals(Optional.empty(), cOne.nextCommand());

        // next command of cTwo should be secondCommand
        assertEquals(optionalSecondCommand, cTwo.nextCommand());

        // next command of cThree should be secondCommand
        assertEquals(optionalSecondCommand, cThree.nextCommand());

        //================== AFTER 1x NEXT COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===========================================================
    }

    @Test
    public void previousCommandThenNextCommandThenPreviousCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalFirstCommand = Optional.of(firstCommand);
        Optional<String> optionalSecondCommand = Optional.of(secondCommand);

        CommandHistory cOne = new CommandHistory(1);
        CommandHistory cTwo = new CommandHistory(2);
        CommandHistory cThree = new CommandHistory(3);

        //========================= PRIOR TO ADDING =========================
        // cOne = {};
        // cOne Current index = -1;
        //
        // cTwo = {};
        // cTwo Current index = -1;
        //
        // cThree = {};
        // cThree Current index = -1;
        //===================================================================

        // Adding firstCommand
        cOne.addToHistory(firstCommand);
        cTwo.addToHistory(firstCommand);
        cThree.addToHistory(firstCommand);

        // Adding secondCommand
        cOne.addToHistory(secondCommand);
        cTwo.addToHistory(secondCommand);
        cThree.addToHistory(secondCommand);

        //================== AFTER ADDING TO HISTORY ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //==============================================================

        // cOne has limit of 1, thus there is no previousCommand
        assertEquals(Optional.empty(), cOne.previousCommand());

        // previous command of cTwo should be firstCommand
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // previous command of cThree should be firstCommand
        assertEquals(optionalFirstCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 0;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 0;
        //===============================================================

        // cOne has limit of 1, thus there is no nextCommand
        assertEquals(Optional.empty(), cOne.nextCommand());

        // nextCommand of cTwo should be secondCommand
        assertEquals(optionalSecondCommand, cTwo.nextCommand());

        // nextCommand of cThree should be secondCommand
        assertEquals(optionalSecondCommand, cThree.nextCommand());

        //==================== AFTER 1x NEXT COMMAND ====================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===============================================================

        // cOne has limit of 1, thus there is no previousCommand
        assertEquals(Optional.empty(), cOne.previousCommand());

        // previous command of cTwo should be firstCommand
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // previous command of cThree should be firstCommand
        assertEquals(optionalFirstCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 0;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 0;
        //===============================================================
    }

    @Test
    public void previousCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalFirstCommand = Optional.of(firstCommand);

        CommandHistory cOne = new CommandHistory(1);
        CommandHistory cTwo = new CommandHistory(2);
        CommandHistory cThree = new CommandHistory(3);

        //========================= PRIOR TO ADDING =========================
        // cOne = {};
        // cOne Current index = -1;
        //
        // cTwo = {};
        // cTwo Current index = -1;
        //
        // cThree = {};
        // cThree Current index = -1;
        //===================================================================

        // since cOne is empty, there is no previousCommand
        assertEquals(Optional.empty(), cOne.previousCommand());

        // since cTwo is empty, there is no previousCommand
        assertEquals(Optional.empty(), cTwo.previousCommand());

        // since cThree is empty, there is no previousCommand
        assertEquals(Optional.empty(), cThree.previousCommand());

        // Adding firstCommand
        cOne.addToHistory(firstCommand);
        cTwo.addToHistory(firstCommand);
        cThree.addToHistory(firstCommand);

        // Adding secondCommand
        cOne.addToHistory(secondCommand);
        cTwo.addToHistory(secondCommand);
        cThree.addToHistory(secondCommand);

        //================== AFTER ADDING TO HISTORY ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //==============================================================

        // cOne has limit of 1, thus there is no previousCommand
        assertEquals(Optional.empty(), cOne.previousCommand());

        // previous command of cTwo should be firstCommand
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // previous command of cThree should be firstCommand
        assertEquals(optionalFirstCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 0;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 0;
        //===============================================================

        // since now current index = 0, there is no previous command
        assertEquals(Optional.empty(), cTwo.previousCommand());

        // since now current index = 0, there is no previous command
        assertEquals(Optional.empty(), cThree.previousCommand());
    }

    @Test
    public void nextCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        CommandHistory cOne = new CommandHistory(1);
        CommandHistory cTwo = new CommandHistory(2);
        CommandHistory cThree = new CommandHistory(3);

        //========================= PRIOR TO ADDING =========================
        // cOne = {};
        // cOne Current index = -1;
        //
        // cTwo = {};
        // cTwo Current index = -1;
        //
        // cThree = {};
        // cThree Current index = -1;
        //===================================================================

        // since cOne is empty, there is no nextCommand
        assertEquals(Optional.empty(), cOne.nextCommand());

        // since cTwo is empty, there is no nextCommand
        assertEquals(Optional.empty(), cTwo.nextCommand());

        // since cThree is empty, there is no nextCommand
        assertEquals(Optional.empty(), cThree.nextCommand());

        // Adding firstCommand
        cOne.addToHistory(firstCommand);
        cTwo.addToHistory(firstCommand);
        cThree.addToHistory(firstCommand);

        // Adding secondCommand
        cOne.addToHistory(secondCommand);
        cTwo.addToHistory(secondCommand);
        cThree.addToHistory(secondCommand);

        //================== AFTER ADDING TO HISTORY ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //==============================================================

        // cOne has limit of 1, thus there is no nextCommand
        assertEquals(Optional.empty(), cOne.nextCommand());

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cTwo.nextCommand());

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cThree.nextCommand());

        //================== AFTER 1x NEXT COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===========================================================

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cTwo.nextCommand());

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cThree.nextCommand());
    }

    @Test
    public void currentCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalFirstCommand = Optional.of(firstCommand);
        Optional<String> optionalSecondCommand = Optional.of(secondCommand);

        CommandHistory cOne = new CommandHistory(1);
        CommandHistory cTwo = new CommandHistory(2);
        CommandHistory cThree = new CommandHistory(3);

        //========================= PRIOR TO ADDING =========================
        // cOne = {};
        // cOne Current index = -1;
        //
        // cTwo = {};
        // cTwo Current index = -1;
        //
        // cThree = {};
        // cThree Current index = -1;
        //===================================================================

        assertEquals(Optional.empty(), cOne.currentCommand());
        assertEquals(Optional.empty(), cTwo.currentCommand());
        assertEquals(Optional.empty(), cThree.currentCommand());

        // Adding firstCommand
        cOne.addToHistory(firstCommand);
        cTwo.addToHistory(firstCommand);
        cThree.addToHistory(firstCommand);

        //=================== AFTER ADDING FIRST COMMAND ===================
        // cOne = {firstCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand};
        // cTwo Current index = 0;
        //
        // cThree = {firstCommand};
        // cThree Current index = 0;
        //==================================================================

        assertEquals(optionalFirstCommand, cOne.currentCommand());
        assertEquals(optionalFirstCommand, cTwo.currentCommand());
        assertEquals(optionalFirstCommand, cThree.currentCommand());


        // Adding secondCommand
        cOne.addToHistory(secondCommand);
        cTwo.addToHistory(secondCommand);
        cThree.addToHistory(secondCommand);

        //================== AFTER ADDING SECOND COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //==================================================================

        assertEquals(optionalSecondCommand, cOne.currentCommand());
        assertEquals(optionalSecondCommand, cTwo.currentCommand());
        assertEquals(optionalSecondCommand, cThree.currentCommand());
    }

    @Test
    public void addToHistory() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";
        String thirdCommand = "ThirdCommand";

        CommandHistory cIsLimitReached = new CommandHistory(1);

        cIsLimitReached.addToHistory(firstCommand);
        cIsLimitReached.addToHistory(secondCommand);

        // c == {secondCommand};
        // c Current index = 0;
        // when overflow, addToHistory should pop firstCommand, thus size() == 1
        assertEquals(cIsLimitReached.getCommandHistory().size(), 1);

        // since length limit is 1, the only item left inside commandHistory should be secondCommand
        assertEquals(cIsLimitReached.getCommandHistory().get(0), secondCommand);


        //======================== ONE MORE TIME FOR GOOD MEASURE ========================

        cIsLimitReached.addToHistory(thirdCommand);
        // c = {thirdCommand}
        // when overflow, addToHistory should now pop secondCommand, thus size() == 1
        assertEquals(1, cIsLimitReached.getCommandHistory().size());

        // since length limit is 1, the only item left inside commandHistory should be thirdCommand
        assertEquals(thirdCommand, cIsLimitReached.getCommandHistory().get(0));
    }

    @Test
    public void overwrite_existing_commandHistory() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";
        String thirdCommand = "ThirdCommand";
        String fourthCommand = "FourthCommand";
        CommandHistory cThree = new CommandHistory(3);

        Optional<String> optionalFourthCommand = Optional.of(fourthCommand);

        cThree.addToHistory(firstCommand);
        cThree.addToHistory(secondCommand);
        cThree.addToHistory(thirdCommand);

        //================== AFTER ADDING TO HISTORY ==================
        // cThree = {firstCommand, secondCommand, thirdCommand};
        // cThree Current index = 2;
        //==============================================================

        cThree.previousCommand();

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cThree = {firstCommand, secondCommand, thirdCommand};
        // cThree Current index = 1;
        //===============================================================

        cThree.addToHistory(fourthCommand);

        //================== AFTER ADDING TO HISTORY ==================
        // cThree = {firstCommand, secondCommand, fourthCommand};
        // cThree Current index = 2;
        //==============================================================

        assertEquals(optionalFourthCommand, cThree.currentCommand());
    }

    @Test
    public void equals() throws HistoryException {
        CommandHistory emptyCommandHistory = new CommandHistory(10);
        CommandHistory emptyCommandHistoryCopy = emptyCommandHistory;
        CommandHistory otherEmptyCommandHistoryButSame = new CommandHistory(10);
        CommandHistory differentEmptyCommandHistory = new CommandHistory(20);

        CommandHistory filledCommandHistory = new CommandHistory(10);
        filledCommandHistory.addToHistory("PLACEHOLDER");

        CommandHistory filledCommandHistoryCopy = filledCommandHistory;

        CommandHistory sameFilledCommandHistory = new CommandHistory(10);
        sameFilledCommandHistory.addToHistory("PLACEHOLDER");

        CommandHistory differentFilledCommandHistory = new CommandHistory(10);
        differentFilledCommandHistory.addToHistory("DIFFERENT");

        // same object -> returns true
        assertTrue(emptyCommandHistory.equals(emptyCommandHistoryCopy));
        assertTrue(filledCommandHistory.equals(filledCommandHistoryCopy));

        // same variable fields -> returns true
        assertTrue(emptyCommandHistory.equals(otherEmptyCommandHistoryButSame));

        // null -> returns false
        assertFalse(emptyCommandHistory.equals(null));

        // different command history -> returns false
        assertFalse(emptyCommandHistory.equals(differentEmptyCommandHistory));

        // empty != filled command history -> returns false
        assertFalse(emptyCommandHistory.equals(filledCommandHistory));

        // differently filled -> returns false
        assertFalse(filledCommandHistory.equals(differentFilledCommandHistory));

        // same fields -> returns true
        assertTrue(filledCommandHistory.equals(sameFilledCommandHistory));
    }

}
