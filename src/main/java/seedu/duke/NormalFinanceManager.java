package seedu.duke;

import seedu.duke.parser.Parser;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.storage.EntryListDataManager;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Logger;

public class FinanceManager {
    public static final String STRING_PROMPT_EDIT = "What would you like to edit?";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final int LENGTH_OF_SEPARATOR = 2;
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String ERROR_INVALID_SORTTYPE = "Please input how you want the list to be sorted.";
    public static final String ERROR_INVALID_SORTDATE = "Please input a valid date.";
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String BLANK = "";

    public ArrayList<Entry> entryList;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";

    public FinanceManager() {
        this.entryList = new ArrayList<>();
    }

    public void addEntry(Entry entry) throws MintException {
        entryList.add(entry);
        try {
            EntryListDataManager.appendToEntryListTextFile(FILE_PATH, entry);
        } catch (IOException e) {
            throw new MintException("Error trying to update external file!");
        }
    }

    public Entry deleteEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        try {
            Entry entry = chooseEntryByKeywords(tags, true, query);
            if (entry != null) {
                deleteEntry(entry);
            }
            return entry;
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public void editEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        try {
            Entry entry = chooseEntryByKeywords(tags, true, query);
            if (entry != null) {
                editEntry(query);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public Entry chooseEntryByKeywords(ArrayList<String> tags, boolean isDelete, Entry query) throws MintException {
        ArrayList<Entry> filteredList = filterEntryByKeywords(tags, query);
        Entry entry = null;
        if (filteredList.size() == 0) {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        } else if (filteredList.size() == 1) {
            Entry onlyEntry = filteredList.get(0);
            if (Ui.isConfirmedToDeleteOrEdit(onlyEntry, isDelete)) {
                entry = onlyEntry;
            }
            return entry;
        }

        Ui.viewGivenList(filteredList);
        try {
            int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
            if (index >= 0) {
                entry = filteredList.get(index);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return entry;
    }

    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(entryList);
        for (String tag : tags) {
            switch (tag) {
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
                filteredList = Filter.filterEntryByCategory(query.getCategory(), filteredList);

                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    public void deleteEntry(Entry entry) {
        //logger.log(Level.INFO, "User deleted expense: " + entry);
        entryList.remove(entry);
        String stringToDelete = overWriteString(entry);
        EntryListDataManager.deleteLineInTextFile(stringToDelete);
    }

    public void editEntry(Entry query) throws MintException {
        String choice;
        int indexToBeChanged;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;
        try {
            final String originalEntryStr = query.toString();
            final String stringToOverwrite = overWriteString(query);
            if (entryList.contains(query)) {
                indexToBeChanged = entryList.indexOf(query);
                choice = scanFieldsToUpdate();
            } else {
                //                logger.log(Level.INFO, "User entered invalid entry");
                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
            }
            editSpecifiedEntry(choice, indexToBeChanged, query);
            // edited
            printEditSuccess = isEditSuccessful(indexToBeChanged, originalEntryStr);
            String stringToUpdate = overWriteString(entryList.get(indexToBeChanged));
            final Entry newEntry = entryList.get(indexToBeChanged);
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

    private void editSpecifiedEntry(String userInput, int indexToBeChanged, Entry entry) throws MintException {
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

    private Boolean isEditSuccessful(int indexToBeChanged, String originalEntry) {
        String newEntry = entryList.get(indexToBeChanged).toString();
        return !originalEntry.equals(newEntry);
    }

    private void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
        try {
            String name = entry.getName();
            LocalDate date = entry.getDate();
            double amount = entry.getAmount();
            Enum category = entry.getCategory();
            for (String word : choice) {
                assert (word != null);
                if (word.contains(NAME_SEPARATOR)) {
                    name = nonEmptyNewDescription(word);
                }
                if (word.contains(DATE_SEPARATOR)) {
                    String dateStr = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    date = LocalDate.parse(dateStr, ValidityChecker.dateFormatter);
                }
                if (word.contains(AMOUNT_SEPARATOR)) {
                    String amountStr = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    amount = Double.parseDouble(amountStr);
                }
                if (word.contains(CATEGORY_SEPARATOR)) {
                    String catNumStr = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    int pos = Integer.parseInt(catNumStr);
                    category = ExpenseCategory.values()[pos];
                }
            }
            entryList.set(index, new Expense(name, date, amount, (ExpenseCategory) category));
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
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

    public void view(String[] argumentArrayInput, RecurringFinanceManager recurringFinanceManager) throws MintException {
        String sortType;
        LocalDate fromDate = null;
        LocalDate endDate = null;
        Month month = null;
        String year = null;
        boolean isViewFrom = false;
        boolean isViewAll = true;
        ArrayList<String> argumentArray;
        ArrayList<Entry> outputArray;
        double total = 0;

        argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));
        outputArray = new ArrayList<>(entryList);

        if (argumentArray.contains("expense")) {
            outputArray.removeIf(entry -> entry.getType() != Type.Expense);
        }

        if (argumentArray.contains("income")) {
            outputArray.removeIf(entry -> entry.getType() != Type.Income);
        }

        if (argumentArray.contains("by")) {
            try {
                sortType = argumentArray.get(argumentArray.indexOf("by") + 1);
                sort(sortType, outputArray);
            } catch (IndexOutOfBoundsException e) {
                throw new MintException(MintException.ERROR_INVALID_SORTTYPE);
            } catch (MintException e) {
                throw new MintException(e.getMessage());
            }
        }

        if (argumentArray.contains("year")) {
            try {
                year = argumentArray.get(argumentArray.indexOf("year") + 1);
            } catch (IndexOutOfBoundsException e) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            System.out.println("For the year " + year + ":");
            Sorter.trimByYear(outputArray, year);
            isViewAll = false;
        }

        if (argumentArray.contains("month")) {
            try {
                month = Month.of(Integer.parseInt(argumentArray.get(argumentArray.indexOf("month") + 1)));
                if (year == null) {
                    year = Integer.toString(LocalDate.now().getYear());
                    Sorter.trimByYear(outputArray, year);
                }
            } catch (DateTimeException e) {
                System.out.println(MintException.ERROR_INVALID_DATE);
                return;
            } catch (IndexOutOfBoundsException e) {
                month = LocalDate.now().getMonth();
            }
            System.out.println("For the month of " + month + ":");
            Sorter.trimByMonth(outputArray, month);
            isViewAll = false;
        }

        if (argumentArray.contains("from")) {
            isViewFrom = true;
            try {
                fromDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 1));
                try {
                    endDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 2));
                } catch (IndexOutOfBoundsException | DateTimeParseException ignored) {
                    endDate = null;
                }
                Sorter.trimFrom(outputArray, fromDate);
                if (endDate != null) {
                    Sorter.trimEnd(outputArray, endDate);
                } else {
                    endDate = LocalDate.now();
                }

            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                System.out.println(MintException.ERROR_INVALID_DATE);
                return;
            }
            recurringFinanceManager.viewRecurringExpenseBetweenTwoDates(outputArray, fromDate,
                    endDate);
            isViewAll = false;
        }

        if (!isViewAll && !isViewFrom) {
            if (year == null) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            if (month == null) {
                recurringFinanceManager.viewRecurringExpenseByYear(outputArray, Integer.parseInt(year));
            } else {
                recurringFinanceManager.viewRecurringExpenseByMonth(outputArray, month.getValue(), Integer.parseInt(year));
            }
        }

        outputArray.sort(Sorter.compareByDate);

        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
            Collections.reverse(outputArray);
        }
        calculateTotal(outputArray);

        Ui.printView(outputArray, fromDate, endDate, total);
    }

    public void sort(String sortType, ArrayList<Entry> outputArray) throws MintException {
        assert sortType != null : "sortType should have a command";
        switch (sortType) {
        case "name":
            outputArray.sort(Sorter.compareByName);
            break;
        case "date":
            outputArray.sort(Sorter.compareByDate);
            break;
        case "amount":
            outputArray.sort(Sorter.compareByAmount);
            break;
        case "category":
            outputArray.sort(Sorter.compareByCategory);
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_COMMAND); // Link to MintException
        }
    }

    public void calculateTotal(ArrayList<Entry> list) {
        double total = 0;
        for (Entry entry: list) {
            if (entry.getType() == Type.Expense) {
                total -= entry.getAmount();
            } else {
                total += entry.getAmount();
            }
        }
    }

    // common method
    public static String overWriteString(Entry entry) {
        return entry.getCategory() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount();
    }

}
