package seedu.duke;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinancialManager {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ArrayList<Entry> entryList;
    public ArrayList<RecurringExpense> recurringExpenses;

    public FinancialManager() {
        this.entryList = new ArrayList<>();
        this.recurringExpenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        entryList.add(expense);
    }

    public void deleteExpense(Expense expense) {
        entryList.remove(expense);
    }

    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry entry) throws MintException {
        ArrayList<Entry> filteredList = entryList;
        for (String tag : tags) {
            switch (tag) {
            case "n/":
                filteredList = Filter.filterEntryByName(entry.getName(), filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(entry.getDate(), filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(entry.getAmount(), filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCategory(entry.getCategory(), filteredList);

                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    public void deleteExpenseByKeywords(ArrayList<String> tags, Expense query) {
        try {
            Expense expense = (Expense) chooseEntryByKeywords(tags, true, query);
            if (expense != null) {
                deleteExpense(expense);
            }
        } catch (MintException e) {
            Ui.printError(e);
        }
    }

    // Common method
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

    public double calculateTotalEntry(ArrayList<Entry> entryList) {
        double total = 0;
        for (Entry entry : entryList) {
            total += entry.getAmount();
        }
        return total;
    }

    // common method
    public static String overWriteString(Entry entry) {
        return entry.getCategory() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount();
    }

    public void sort(ArrayList<Entry> outputArray, String sortType) throws MintException {
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
            throw new MintException(MintException.ERROR_INVALID_COMMAND);
        }
    }

    public void viewExpense(String[] argumentArrayInput)  {
        String sortType;
        LocalDate fromDate;
        LocalDate endDate;
        Month month;
        String year = null;
        ArrayList<String> argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));
        ArrayList<Entry> outputArray = new ArrayList<Entry>(entryList);

        if (argumentArray.contains("by")) {
            try {
                sortType = argumentArray.get(argumentArray.indexOf("by") + 1);
                sort(outputArray, sortType);
            } catch (IndexOutOfBoundsException | MintException e) {
                System.out.println(MintException.ERROR_INVALID_SORTTYPE);
                return;
            }
        }

        System.out.println("Here is the list of your expenses:");

        if (argumentArray.contains("year")) {
            try {
                year = argumentArray.get(argumentArray.indexOf("year") + 1);
            } catch (IndexOutOfBoundsException e) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            System.out.println("For the year " + year + ":");
            Sorter.trimByYear(outputArray, year);
        }

        if (argumentArray.contains("month")) {
            try {
                month = Month.of(Integer.parseInt(argumentArray.get(argumentArray.indexOf("month") + 1)));
                if (year == null) {
                    year = Integer.toString(LocalDate.now().getYear());
                    Sorter.trimByYear(outputArray, year);
                }
            } catch (IndexOutOfBoundsException e) {
                month = LocalDate.now().getMonth();
            }
            System.out.println("For the month of " + month + ":");
            Sorter.trimByMonth(outputArray, month);
            if (year == null) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            //recurringExpenseList.viewRecurringExpenseByMonth(outputArray, month.getValue(),
            //        Integer.parseInt(year));
        }

        if (argumentArray.contains("from")) {
            try {
                fromDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 1));
                try {
                    endDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 2));
                } catch (IndexOutOfBoundsException | DateTimeParseException ignored) {
                    endDate = null;
                }
                System.out.print("Since " + fromDate);
                Sorter.trimFrom(outputArray, fromDate);
                if (endDate != null) {
                    Sorter.trimEnd(outputArray, endDate);
                    System.out.print(" to " + endDate);
                }
                System.out.println();

            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                System.out.println(MintException.ERROR_INVALID_SORTDATE);
                return;
            }
            //recurringExpenseList.viewRecurringExpenseBetweenTwoDates(outputArray, fromDate,
            //        endDate);
        }

        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
            Collections.reverse(outputArray);
        }

        for (Entry entry : outputArray) {
            System.out.println(entry);
        }
    }
}
