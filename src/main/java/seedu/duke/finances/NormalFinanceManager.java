package seedu.duke.finances;

import seedu.duke.entries.*;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.utility.Filter;
import seedu.duke.utility.Sorter;
import seedu.duke.utility.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NormalFinanceManager extends FinanceManager {

    public ArrayList<Entry> entryList;
    public static final String NORMAL_FILE_PATH = "data" + File.separator + "Mint.txt";

    public NormalFinanceManager() {
        this.entryList = new ArrayList<>();
    }

    public void addEntry(Entry entry) throws MintException {
        NormalListDataManager normalListDataManager = new NormalListDataManager(NORMAL_FILE_PATH);
        entryList.add(entry);
        normalListDataManager.appendToEntryListTextFile(NORMAL_FILE_PATH, entry);
    }

    public ArrayList<Entry> getEntryList() {
        return entryList;
    }

    @Override
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

    @Override
    public void deleteEntry(Entry entry) {
        //logger.log(Level.INFO, "User deleted expense: " + entry);
        NormalListDataManager normalListDataManager = new NormalListDataManager(NORMAL_FILE_PATH);
        entryList.remove(entry);
        String stringToDelete = overWriteString(entry);
        normalListDataManager.deleteLineInTextFile(stringToDelete);
    }

    @Override
    public void editEntry(Entry query) throws MintException {
        String choice;
        int indexToBeChanged;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;
        NormalListDataManager normalListDataManager = new NormalListDataManager(NORMAL_FILE_PATH);
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
            normalListDataManager.editEntryListTextFile(stringToOverwrite, stringToUpdate);
        } catch (NumberFormatException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_NUMBER);
        } catch (DateTimeParseException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_DATE);
        }
        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
    }

    protected Boolean isEditSuccessful(int indexToBeChanged, String originalEntry) {
        String newEntry = entryList.get(indexToBeChanged).toString();
        return !originalEntry.equals(newEntry);
    }

    @Override
    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
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
            if (entry.getType() == Type.Expense) {
                entryList.set(index, new Expense(name, date, amount, (ExpenseCategory) category));
            } else {
                entryList.set(index, new Income(name, date, amount, (IncomeCategory) category));
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public void view(String[] argumentArrayInput,
                     RecurringFinanceManager recurringFinanceManager) throws MintException {
        String sortType;
        LocalDate fromDate = null;
        LocalDate endDate = null;
        Month month = null;
        String year = null;
        boolean isViewFrom = false;
        boolean isViewAll = true;
        ArrayList<String> argumentArray;
        ArrayList<Entry> outputArray;

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
        if (isViewAll) {
            recurringFinanceManager.viewAllRecurringExpense(outputArray);
        } else if (!isViewAll && !isViewFrom) {
            if (year == null) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            if (month == null) {
                recurringFinanceManager.viewRecurringExpenseByYear(outputArray, Integer.parseInt(year));
            } else {
                recurringFinanceManager.viewRecurringEntryByMonth(outputArray, month.getValue(),
                        Integer.parseInt(year));
            }
        }

        outputArray.sort(Sorter.compareByDate);

        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
            Collections.reverse(outputArray);
        }
        double total = 0;
        calculateTotal(outputArray);

        Ui.printView(outputArray, fromDate, endDate, total);
        if (isViewAll) {
            Ui.printViewRecurring(recurringFinanceManager.recurringEntryList);
        }
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

    // common method
    public static String overWriteString(Entry entry) {
        return entry.getType() + "|" + entry.getCategory().ordinal() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount();
    }

    public void loadPreviousFileContents() {
        DataManagerActions dataManagerActions = new DataManagerActions(NORMAL_FILE_PATH);
        NormalListDataManager normalListDataManager = new NormalListDataManager(NORMAL_FILE_PATH);
        try {
            normalListDataManager.loadEntryListContents(entryList);
        } catch (FileNotFoundException e) {
            Ui.printMissingFileMessage();
            dataManagerActions.createDirectory();
            dataManagerActions.createFiles();
        }
    }

}
