package seedu.duke.model.financemanager;

import seedu.duke.utility.MintException;
import seedu.duke.model.entries.Entry;
import seedu.duke.logic.parser.Parser;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class that deals with operations on entries. Super class of NormalFinanceManger and RecurringFinanceManager.
 * Contains common method of these two classes.
 */
public abstract class FinanceManager {
    public static final String STRING_PROMPT_EDIT = "What would you like to edit? "
            + "Type the tag and what you want to change e.g. a/10";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final int LENGTH_OF_SEPARATOR = 2;
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String BLANK = "";
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void deleteEntry(Entry entry) {

    }

    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        return null;
    }

    public ArrayList<String> editEntry(Entry query) throws MintException {
        return null;
    }

    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {

    }

    //@@author Yitching
    /**
     * Splits the user input into the different fields via tags.
     *
     * @param userInput is the user input containing the expense that the user wishes to edit.
     * @param indexToBeChanged is the index of the entry to be edited
     * @param entry Entry type variable that contains all the attributes of the
     *     recurring expense.
     */
    public void editSpecifiedEntry(String userInput, int indexToBeChanged, Entry entry) throws MintException {
        Parser parser = new Parser();
        ArrayList<String> splitChoice = new ArrayList<>();
        String choice = " " + userInput;
        while (choice.matches(userTagRaw)) {
            int currentIndex = parser.getCurrentTagIndex(choice);
            int nextIndex = choice.length();

            if (parser.hasNextTag(choice, currentIndex)) {
                nextIndex = parser.getNextTagIndex(choice, currentIndex);
            }
            choice = remainingString(splitChoice, choice, currentIndex, nextIndex);
        }
        try {
            amendEntry(indexToBeChanged, splitChoice, entry);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    /**
     * This function stores the part of the string that has not been extracted, to be extracted later.
     *
     * @param splitChoice is the user input that has been split by tags.
     * @param choice is the remaining userInput taht has yet to be extracted, to be extracted later.
     * @param currentIndex is the index of the current flag that has been identified.
     * @param nextIndex is the index of the next flag that has been identified.
     *
     * @return returns the remaining string that has yet to be extracted, to be extracted later.
     */
    public String remainingString(ArrayList<String> splitChoice, String choice, int currentIndex, int nextIndex) {
        String description;
        description = choice.substring(currentIndex, nextIndex).trim();
        extractFieldsToAmend(splitChoice, description);
        choice = choice.substring(nextIndex);
        return choice;
    }

    /**
     * This function adds all the attributes that the user wishes to amend.
     *
     * @param splitChoice is the user input that has been split by tags.
     * @param description is the specific attribute to be amended.
     */
    public void extractFieldsToAmend(ArrayList<String> splitChoice, String description) {
        splitChoice.add(description);
    }

    /**
     * This function scans for the user input of attributes that user wishes to amend.
     *
     * @return the fields that the user wishes to amend.
     */
    public String scanFieldsToUpdate() {
        String choice;
        Scanner scan = new Scanner(System.in);
        System.out.println(STRING_PROMPT_EDIT);
        choice = scan.nextLine();
        return choice;
    }

    /**
     * This function checks if the description field for description tag is empty.
     *
     * @param word is the description that the user has input that he/she wishes to eidt
     *
     * @return the description field if it is not empty.
     */
    protected String nonEmptyNewDescription(String word) throws MintException {
        String description;
        String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
        if (!newDescription.trim().equalsIgnoreCase(BLANK)) {
            description = newDescription.trim();
        } else {
            throw new MintException(ERROR_INVALID_DESCRIPTION);
        }
        return description;
    }
}
//@@author Yitching
