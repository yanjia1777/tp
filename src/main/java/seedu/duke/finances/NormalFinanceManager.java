package seedu.duke.finances;

import seedu.duke.entries.Entry;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.parser.Parser;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.utility.Filter;
import seedu.duke.utility.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Override
    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        assert tags.size() > 0 : "There should be more than one tag to be queried";
        ArrayList<Entry> filteredList = new ArrayList<>(entryList);
        for (String tag : tags) {
            switch (tag.trim()) {
            case "n/":
                filteredList = Filter.filterEntryByName(query.getName(), filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(query.getDate(), filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(query.getAmount(), filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCategory(query.getCategory().ordinal(), filteredList);
                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

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
        int indexToBeChanged = 0;
        String originalEntryStr = "";
        originalEntryStr = overWriteString(entry);
        if (entryList.contains(entry)) {
            indexToBeChanged = entryList.indexOf(entry);
            choice = scanFieldsToUpdate();
        } else {
            //                logger.log(Level.INFO, "User entered invalid entry");
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
        }
        ValidityChecker.checkTagsFormatSpacing(choice);
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
                if (word.contains(NAME_SEPARATOR)) {
                    count++;
                    String name = nonEmptyNewDescription(word);
                    entryFields.put("name", name);
                } else if (word.contains(DATE_SEPARATOR)) {
                    count++;
                    String dateStr = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("date", dateStr);
                } else if (word.contains(AMOUNT_SEPARATOR)) {
                    count++;
                    String amountStr = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("amount",amountStr);
                } else if (word.contains(CATEGORY_SEPARATOR)) {
                    count++;
                    String catNumStr = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
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

    //@@author
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
