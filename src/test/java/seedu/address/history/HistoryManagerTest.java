package seedu.address.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.history.exception.HistoryException;

public class HistoryManagerTest {

    @Test
    public void constructorInvalidLengthLimit_throwsHistoryException() {
        // instantiating with a negative limit -> throws HistoryException
        assertThrows(HistoryException.class, () -> new HistoryManager(-1));

        // instantiating with a limit of 0 -> throws HistoryException
        assertThrows(HistoryException.class, () -> new HistoryManager(0));
    }

    @Test
    public void nextCommandThenPreviousCommandThenNextCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalFirstCommand = Optional.of(firstCommand);
        Optional<String> optionalSecondCommand = Optional.of(secondCommand);

        HistoryManager cOne = new HistoryManager(1);
        HistoryManager cTwo = new HistoryManager(2);
        HistoryManager cThree = new HistoryManager(3);

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

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cTwo.previousCommand());

        // previous command of cThree should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
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
    }

    @Test
    public void previousCommandThenPreviousCommandThenNextCommandThenPreviousCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalFirstCommand = Optional.of(firstCommand);
        Optional<String> optionalSecondCommand = Optional.of(secondCommand);

        HistoryManager cOne = new HistoryManager(1);
        HistoryManager cTwo = new HistoryManager(2);
        HistoryManager cThree = new HistoryManager(3);

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

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cTwo.previousCommand());

        // previous command of cThree should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===============================================================

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be firstCommand as it's not the first time we are applying previousCommand
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // previous command of cThree should be firstCommand as it's not the first time we are applying previousCommand
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

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cOne.nextCommand());

        // next Item in both cTwo and cThree should be secondCommand
        assertEquals(optionalSecondCommand, cTwo.nextCommand());
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

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be firstCommand as it's not the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // previous command of cThree should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalFirstCommand, cThree.previousCommand());
    }

    @Test
    public void previousCommandThenNextCommandThenPreviousCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalSecondCommand = Optional.of(secondCommand);

        HistoryManager cOne = new HistoryManager(1);
        HistoryManager cTwo = new HistoryManager(2);
        HistoryManager cThree = new HistoryManager(3);

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

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cTwo.previousCommand());

        // previous command of cThree should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===============================================================

        // cOne has limit of 1, thus there is no nextCommand
        assertEquals(Optional.empty(), cOne.nextCommand());

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cTwo.nextCommand());

        // since currentCommandIndex is at the end, there is no nextCommand
        assertEquals(Optional.empty(), cThree.nextCommand());

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

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cTwo.previousCommand());

        // previous command of cThree should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===============================================================
    }

    @Test
    public void previousCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        Optional<String> optionalFirstCommand = Optional.of(firstCommand);
        Optional<String> optionalSecondCommand = Optional.of(secondCommand);

        HistoryManager cOne = new HistoryManager(1);
        HistoryManager cTwo = new HistoryManager(2);
        HistoryManager cThree = new HistoryManager(3);

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

        //================== AFTER ADDING FIRST COMMAND ==================
        // cOne = {firstCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand};
        // cTwo Current index = 0;
        //
        // cThree = {firstCommand};
        // cThree Current index = 0;
        //=================================================================

        // if only command in history, return that command
        assertEquals(optionalFirstCommand, cOne.previousCommand());
        assertEquals(optionalFirstCommand, cTwo.previousCommand());
        assertEquals(optionalFirstCommand, cThree.previousCommand());

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
        //=================================================================

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cTwo.previousCommand());

        // previous command of cThree should be secondCommand as it's the first time
        // we are applying previousCommand after addingToHistory
        assertEquals(optionalSecondCommand, cThree.previousCommand());

        //================== AFTER 1x PREVIOUS COMMAND ==================
        // cOne = {secondCommand};
        // cOne Current index = 0;
        //
        // cTwo = {firstCommand, secondCommand};
        // cTwo Current index = 1;
        //
        // cThree = {firstCommand, secondCommand};
        // cThree Current index = 1;
        //===============================================================

        // if only command in history, return that command
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // previous command of cTwo should be firstCommand as it's not the first time we're prompting previousCommand
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // previous command of cTwo should be firstCommand as it's not the first time we're prompting previousCommand
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

        // since currentIndex is 0, return secondCommand for cOne
        assertEquals(optionalSecondCommand, cOne.previousCommand());

        // since currentIndex is 0, return firstCommand for cTwo
        assertEquals(optionalFirstCommand, cTwo.previousCommand());

        // since currentIndex is 0, return firstCommand for cThree
        assertEquals(optionalFirstCommand, cThree.previousCommand());
    }

    @Test
    public void nextCommand() throws HistoryException {
        String firstCommand = "FirstCommand";
        String secondCommand = "SecondCommand";

        HistoryManager cOne = new HistoryManager(1);
        HistoryManager cTwo = new HistoryManager(2);
        HistoryManager cThree = new HistoryManager(3);

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

        HistoryManager cOne = new HistoryManager(1);
        HistoryManager cTwo = new HistoryManager(2);
        HistoryManager cThree = new HistoryManager(3);

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

        HistoryManager cIsLimitReached = new HistoryManager(1);

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
        HistoryManager cThree = new HistoryManager(3);

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
        HistoryManager emptyHistoryManager = new HistoryManager(10);
        HistoryManager emptyHistoryManagerCopy = emptyHistoryManager;
        HistoryManager otherEmptyHistoryManagerButSame = new HistoryManager(10);
        HistoryManager differentEmptyHistoryManager = new HistoryManager(20);

        HistoryManager filledHistoryManager = new HistoryManager(10);
        filledHistoryManager.addToHistory("PLACEHOLDER");

        HistoryManager filledHistoryManagerCopy = filledHistoryManager;

        HistoryManager sameFilledHistoryManager = new HistoryManager(10);
        sameFilledHistoryManager.addToHistory("PLACEHOLDER");

        HistoryManager differentFilledHistoryManager = new HistoryManager(10);
        differentFilledHistoryManager.addToHistory("DIFFERENT");

        // same object -> returns true
        assertTrue(emptyHistoryManager.equals(emptyHistoryManagerCopy));
        assertTrue(filledHistoryManager.equals(filledHistoryManagerCopy));

        // same variable fields -> returns true
        assertTrue(emptyHistoryManager.equals(otherEmptyHistoryManagerButSame));

        // null -> returns false
        assertFalse(emptyHistoryManager.equals(null));

        // different command history -> returns false
        assertFalse(emptyHistoryManager.equals(differentEmptyHistoryManager));

        // empty != filled command history -> returns false
        assertFalse(emptyHistoryManager.equals(filledHistoryManager));

        // differently filled -> returns false
        assertFalse(filledHistoryManager.equals(differentFilledHistoryManager));

        // same fields -> returns true
        assertTrue(filledHistoryManager.equals(sameFilledHistoryManager));
    }

}
