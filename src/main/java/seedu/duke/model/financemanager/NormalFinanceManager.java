package seedu.duke.model.financemanager;

import seedu.duke.model.entries.Entry;
import seedu.duke.model.entries.Type;
import seedu.duke.utility.MintException;
import seedu.duke.logic.parser.Parser;
import seedu.duke.logic.parser.ValidityChecker;
import seedu.duke.utility.Filter;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that deals with operations on normal entries. Stores a list of entries.
 */
public class NormalFinanceManager extends FinanceManager {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ArrayList<Entry> entryList;

    public NormalFinanceManager() {
        this.entryList = new ArrayList<>();
    }

    public void addEntry(Entry entry) throws MintException {
        entryList.add(entry);
    }

    public ArrayList<Entry> getEntryList() {
        return entryList;
    }

    //@@author pos0414
    /**
     * Filters the normal entries that matches the fields that user queried, from the list of stored normal entries.
     * @param tags List of tags that user has queried.
     * @param query Entry object that contains the details of the query that user has made.
     * @return List of matching entries
     * @throws MintException If tag given is invalid
     */
    @Override
    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        assert tags.size() > 0 : "There should be more than one tag to be queried";
        ArrayList<Entry> filteredList = new ArrayList<>(entryList);
        for (String tag : tags) {
            switch (tag.trim()) {
            case NAME_TAG:
                filteredList = Filter.filterEntryByName(query.getName(), filteredList);
                break;
            case DATE_TAG:
                filteredList = Filter.filterEntryByDate(query.getDate(), filteredList);
                break;
            case AMOUNT_TAG:
                filteredList = Filter.filterEntryByAmount(query.getAmount(), filteredList);
                break;
            case CATEGORY_TAG:
                filteredList = Filter.filterEntryByCategory(query.getCategory().ordinal(), filteredList);
                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    /**
     * Deletes the entry in the entryList. To reach this point all validity checks and the existence of the
     * entry inside the entryList have been checked. Thus assume the entry exists in the list.
     * @param entry Entry to be deleted.
     */
    @Override
    public void deleteEntry(Entry entry) {
        assert entryList.contains(entry) : "entryList should contain the entry to delete.";
        logger.log(Level.INFO, "User deleted entry: " + entry);
        entryList.remove(entry);
    }

    //@@author Yitching
    /**
     * Calls all the methods required for edit.
     *
     * @param entry Entry type variable that contains all the attributes of the expense.
     *
     * @return returns an ArrayList containing the string to be overwritten in the external text file and the new
     *     string to overwrite the old string in the external text file.
     */
    @Override
    public ArrayList<String> editEntry(Entry entry) throws MintException {
        String choice;
        int indexToBeChanged;
        String originalEntryStr;
        originalEntryStr = overWriteString(entry);
        if (entryList.contains(entry)) {
            indexToBeChanged = entryList.indexOf(entry);
            choice = scanFieldsToUpdate();
        } else {
            //                logger.log(Level.INFO, "User entered invalid entry");
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
        }
        ValidityChecker.checkTagsFormatSpacing(choice);
        Parser parser = new Parser();
        parser.checkDuplicateTagsForNormalEdit(choice);
        editSpecifiedEntry(choice, indexToBeChanged, entry);
        String newEntryStr = overWriteString(entryList.get(indexToBeChanged));
        Ui.printOutcomeOfEditAttempt();
        return new ArrayList<>(Arrays.asList(originalEntryStr, newEntryStr));
    }

    /**
     * Splits user input into the respective fields via tags.
     *
     * @param index the index of the entryList to be edited.
     * @param choice user input containing the fields user wishes to edit.
     * @param entry Entry type variable that contains all the attributes of the expense.
     */
    @Override
    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
        try {
            Parser parser = new Parser();
            HashMap<String, String> entryFields = parser.prepareEntryToAmendForEdit(entry);
            Type type = entry.getType();

            int count = 0;
            for (String word : choice) {
                assert (word != null);
                if (word.contains(NAME_TAG)) {
                    count++;
                    String name = nonEmptyNewDescription(word);
                    entryFields.put("name", name);
                } else if (word.contains(DATE_TAG)) {
                    count++;
                    String dateStr = word.substring(word.indexOf(DATE_TAG) + LENGTH_OF_TAG).trim();
                    entryFields.put("date", dateStr);
                } else if (word.contains(AMOUNT_TAG)) {
                    count++;
                    String amountStr = word.substring(word.indexOf(AMOUNT_TAG) + LENGTH_OF_TAG).trim();
                    entryFields.put("amount",amountStr);
                } else if (word.contains(CATEGORY_TAG)) {
                    count++;
                    String catNumStr = word.substring(word.indexOf(CATEGORY_TAG) + LENGTH_OF_TAG).trim();
                    entryFields.put("catNum", catNumStr);
                }
            }
            if (count == 0) {
                throw new MintException("No valid fields entered!");
            }
            setEditedEntry(index, entryFields, type);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    /**
     * Checks validity of the new entry to be used to overwrite the old entry.
     *
     * @param index the index of the entryList to be edited.
     * @param entryFields HashMap containing all the String type attributes.
     * @param type refers to whether it is an expense or an income.
     */
    private void setEditedEntry(int index, HashMap<String, String> entryFields, Type type) throws MintException {
        Parser parser = new Parser();
        String name = entryFields.get("name");
        String dateStr = entryFields.get("date");
        String amountStr = entryFields.get("amount");
        String catNumStr = entryFields.get("catNum");

        ValidityChecker.checkValidityOfFieldsInNormalListTxt("expense", name, dateStr, amountStr, catNumStr);
        Entry entry = parser.convertEntryToRespectiveTypes(entryFields, type);
        entryList.set(index, entry);
    }

    //@@author yanjia1777
    public ArrayList<Entry> getCopyOfArray() {
        ArrayList<Entry> outputArray;
        outputArray = new ArrayList<>(entryList);
        return outputArray;
    }

    // common method
    public static String overWriteString(Entry entry) {
        return entry.getType() + "|" + entry.getCategory().ordinal() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount();
    }

    public void deleteAll() {
        entryList.clear();
    }
}
