package seedu.address.logic.commands.deliverycommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.delivery.predicate.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.deliverymodel.DeliveryModel;
import seedu.address.model.deliverymodel.DeliveryModelManager;
import seedu.address.testutil.DeliveryBuilder;

class DeliveryFindCommandTest {

    private static final Delivery KELVIN = new DeliveryBuilder().withName("Kelvin")
            .withPhone("91234332")
            .withAddress("Clementi Blk 235 #11-111")
            .withOrder("Char Kway Teow")
            .build();
    private static final Delivery MARCUS = new DeliveryBuilder().withName("Marcus")
            .withPhone("8198264")
            .withAddress("Jurong Blk 231 #15-123")
            .withOrder("Seafood Hor Fun x5")
            .build();
    private DeliveryModel deliveryModel = new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
    private DeliveryModel expectedDeliveryModel =
            new DeliveryModelManager(getTypicalDeliveryBook(), new UserPrefs());
    private DeliveryModel testModel;
    private DeliveryModel expectedTestModel;

    @BeforeEach
    public void setUp() {
        testModel = new DeliveryModelManager();
        expectedTestModel = new DeliveryModelManager();
        testModel.addDelivery(KELVIN);
        testModel.addDelivery(MARCUS);
        expectedTestModel.addDelivery(KELVIN);
        expectedTestModel.addDelivery(MARCUS);
    }

    @Test
    public void execute_zeroKeywords_noItemFound() {
        executeTest(0, prepareDeliveryNamePredicate(" "),
                deliveryModel, expectedDeliveryModel, Collections.emptyList());
    }

    @Test
    public void execute_matchingSubstring_itemsFound() {
        executeTest(2, prepareAddressPredicate("blk 23"), testModel, expectedTestModel,
                testModel.getFilteredAndSortedDeliveryList());
    }

    @Test
    public void execute_matchingKeywordsNotInOrder_itemsFound() {
        executeTest(2, prepareAddressPredicate("23 blk"), testModel, expectedTestModel,
                testModel.getFilteredAndSortedDeliveryList());
    }

    @Test
    public void execute_twoFieldSpecified_itemsFound() {
        executeTest(1, prepareDeliveryNamePredicate("Kelvin")
                .and(prepareAddressPredicate("Clementi")), testModel,
                expectedTestModel, testModel.getFilteredAndSortedDeliveryList());
    }

    @Test
    void equals() {
        DeliveryNameContainsKeywordsPredicate firstPredicate =
                new DeliveryNameContainsKeywordsPredicate(Collections.singletonList("first"));
        DeliveryNameContainsKeywordsPredicate secondPredicate =
                new DeliveryNameContainsKeywordsPredicate(Collections.singletonList("second"));
        DeliveryFindCommand findFirstCommand = new DeliveryFindCommand(firstPredicate);
        DeliveryFindCommand findSecondCommand = new DeliveryFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        DeliveryFindCommand findFirstCommandCopy = new DeliveryFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different delivery -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code DeliveryNameContainsKeywordsPredicate}.
     */
    private DeliveryNameContainsKeywordsPredicate prepareDeliveryNamePredicate(String userInput) {
        return new DeliveryNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Carries out an {@code execute} test with relevant arguments.
     */
    private void executeTest(int numItemsMatched, Predicate<Delivery> predicate,
                             DeliveryModel actualModel, DeliveryModel expectedModel,
                             List<Delivery> expectedFilteredDeliveries) {

        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, numItemsMatched);
        DeliveryFindCommand command = new DeliveryFindCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);

        assertCommandSuccess(command, actualModel, expectedMessage, expectedModel);
        assertEquals(expectedFilteredDeliveries, actualModel.getFilteredAndSortedDeliveryList());
    }

}
