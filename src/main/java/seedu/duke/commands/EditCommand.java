package seedu.duke.commands;


import seedu.duke.Entry;
import seedu.duke.CategoryList;
import seedu.duke.Ui;
import seedu.duke.EntryList;
import seedu.duke.MintException;
import seedu.duke.storage.EntryListDataManager;
import seedu.duke.parser.Parser;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditCommand extends Command {

    public static final String STRING_PROMPT_EDIT = "What would you like to edit?";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final String BLANK = "";
    public static final int LENGTH_OF_SEPARATOR = 2;
    protected static final String ERROR_EXPENSE_NOT_IN_LIST = "Hmm.. That item is not in the list.";


    public void editByKeywords(ArrayList<String> tags, Entry entry,
                               ArrayList<Entry> entryList) throws MintException {
        try {
            String name = entry.getName();
            String date = entry.getDate().toString();
            String amount = Double.toString(entry.getAmount());
            String catNum = Integer.toString(entry.getCatNum());
            Entry finalEntry = EntryList.chooseEntryByKeywords(tags, true, name, date, amount, catNum,
                    entryList);
            if (finalEntry != null) {
                edit(finalEntry, entryList);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }


    //    public void editExpense(Expense expense) throws MintException {
    //        editExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }

    public void edit(Entry entry, ArrayList<Entry> entryList) throws MintException {
        String choice;
        int indexToBeChanged;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;
        try {
            String name = entry.getName();
            String date = entry.getDate().toString();
            String amount = Double.toString(entry.getAmount());
            String catNum = Integer.toString(entry.getCatNum());
            Entry originalEntry = new Entry(name, date, amount, catNum);
            final String originalEntryStr = originalEntry.toString();
            final String stringToOverwrite = EntryList.overWriteString(originalEntry);
            if (entryList.contains(originalEntry)) {
                indexToBeChanged = entryList.indexOf(originalEntry);
                choice = scanFieldsToUpdate();
            } else {
                //                logger.log(Level.INFO, "User entered invalid entry");
                throw new MintException(ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
            }
            editSpecifiedEntry(choice, indexToBeChanged, originalEntry, entryList);
            // edited
            printEditSuccess = isEditSuccessful(indexToBeChanged, originalEntryStr, entryList);
            String stringToUpdate = EntryList.overWriteString(entryList.get(indexToBeChanged));
            final Entry newEntry = entryList.get(indexToBeChanged);
            CategoryList.editSpending(originalEntry, newEntry);
            EntryListDataManager.editEntryListTextFile(stringToOverwrite, stringToUpdate);

        } catch (NumberFormatException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_NUMBER);
        } catch (DateTimeParseException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_DATE);
        }
        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
    }

    private void editSpecifiedEntry(String userInput, int indexToBeChanged, Entry entry,
                                    ArrayList<Entry> entryList) throws MintException {
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
        amendEntry(indexToBeChanged, splitChoice, entry, entryList);
    }

    private String remainingString(ArrayList<String> splitChoice, String choice, int currentIndex, int nextIndex) {
        String description;
        description = choice.substring(currentIndex, nextIndex).trim();
        extractFieldsToAmend(splitChoice, description);
        choice = choice.substring(nextIndex);
        return choice;
    }

    private void extractFieldsToAmend(ArrayList<String> splitChoice, String description) {
        splitChoice.add(description);
    }

    private String scanFieldsToUpdate() {
        String choice;
        Scanner scan = new Scanner(System.in);
        System.out.println(STRING_PROMPT_EDIT);
        choice = scan.nextLine();
        return choice;
    }

    private Boolean isEditSuccessful(int indexToBeChanged, String originalEntry, ArrayList<Entry> entryList) {
        String newEntry = entryList.get(indexToBeChanged).toString();
        return !originalEntry.equals(newEntry);
    }

    private void amendEntry(int index, ArrayList<String> choice, Entry entry,
                              ArrayList<Entry> entryList) throws MintException {
        String name = entry.getName();
        String date = entry.getDate().toString();
        String amount = Double.toString(entry.getAmount());
        String catNum = Integer.toString(entry.getCatNum());
        for (String word : choice) {
            assert (word != null);
            if (word.contains(NAME_SEPARATOR)) {
                name = nonEmptyNewDescription(word);
            }
            if (word.contains(DATE_SEPARATOR)) {
                date = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(AMOUNT_SEPARATOR)) {
                amount = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(CATEGORY_SEPARATOR)) {
                catNum = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                CategoryList.checkValidCatNum(catNum);
            }
        }
        entryList.set(index, new Entry(name, date, amount, catNum));
    }

    private String nonEmptyNewDescription(String word) throws MintException {
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
