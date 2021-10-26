package seedu.duke.finances;

import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.parser.Parser;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.parser.ViewOptions;
import seedu.duke.utility.Filter;
import seedu.duke.utility.Sorter;
import seedu.duke.utility.Ui;
import seedu.duke.parser.ViewOptions;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NormalFinanceManager extends FinanceManager {

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
    public Entry chooseEntryByKeywords(ArrayList<String> tags, boolean isDelete, Entry query) throws MintException {
        ArrayList<Entry> filteredList = filterEntryByKeywords(tags, query);
        Entry entry;
        if (filteredList.size() == 0) {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);

        } else if (filteredList.size() == 1) {
            Entry onlyEntry = filteredList.get(0);
            if (Ui.isConfirmedToDeleteOrEdit(onlyEntry, isDelete)) {
                entry = onlyEntry;
            } else {
                throw new MintException("Ok. I have cancelled the process.");
            }
            return entry;
        }

        Ui.viewGivenList(filteredList);
        try {
            int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
            if (index >= 0) {
                entry = filteredList.get(index);
            } else {
                throw new MintException("Ok. I have cancelled the process.");
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
        entryList.remove(entry);
    }

    @Override
    public ArrayList<String> editEntry(Entry entry) throws MintException {
        String choice;
        int indexToBeChanged = 0;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;
        String originalEntryStr = "";
        Parser parser = new Parser();
        try {
            originalEntryStr = overWriteString(entry);
            if (entryList.contains(entry)) {
                indexToBeChanged = entryList.indexOf(entry);
                choice = scanFieldsToUpdate();
            } else {
                //                logger.log(Level.INFO, "User entered invalid entry");
                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
            }
            editSpecifiedEntry(choice, indexToBeChanged, entry);
            // edited
            printEditSuccess = isEditSuccessful(indexToBeChanged, originalEntryStr);
        } catch (NumberFormatException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_NUMBER);
        } catch (DateTimeParseException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_DATE);
        }
        String newEntryStr = overWriteString(entryList.get(indexToBeChanged));
        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
        return new ArrayList<>(Arrays.asList(originalEntryStr, newEntryStr));
    }

    protected Boolean isEditSuccessful(int indexToBeChanged, String originalEntry) {
        String newEntry = entryList.get(indexToBeChanged).toString();
        return !originalEntry.equals(newEntry);
    }

    @Override
    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
        try {
            Type type = entry.getType();
            String name = entry.getName();
            LocalDate date = entry.getDate();
            double amount = entry.getAmount();
            Enum category = entry.getCategory();
            int count = 0;
            for (String word : choice) {
                assert (word != null);
                if (word.contains(NAME_SEPARATOR)) {
                    count++;
                    name = nonEmptyNewDescription(word);
                }
                if (word.contains(DATE_SEPARATOR)) {
                    count++;
                    String dateStr = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    date = LocalDate.parse(dateStr, ValidityChecker.dateFormatter);
                }
                if (word.contains(AMOUNT_SEPARATOR)) {
                    count++;
                    String amountStr = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    amount = Double.parseDouble(amountStr);
                }
                if (word.contains(CATEGORY_SEPARATOR)) {
                    count++;
                    String catNumStr = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    int pos = Integer.parseInt(catNumStr);
                    category = type == Type.Expense ? ExpenseCategory.values()[pos] : IncomeCategory.values()[pos];
                }
            }
            if (count == 0) {
                throw new MintException("No valid fields entered!");
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

    public ArrayList<Entry> view(ViewOptions viewOptions) throws MintException {
        ArrayList<Entry> outputArray;
        outputArray = new ArrayList<>(entryList);

        if (viewOptions.onlyExpense) {
            outputArray.removeIf(entry -> entry.getType() != Type.Expense);
        }

        if (viewOptions.onlyIncome) {
            outputArray.removeIf(entry -> entry.getType() != Type.Income);
        }

        if (viewOptions.year != 0) {
            System.out.println("For the year " + viewOptions.year + ":");
            Sorter.trimByYear(outputArray, viewOptions.year);
        }

        if (viewOptions.month != null) {
            System.out.println("For the month of " + viewOptions.month + ":");
            Sorter.trimByMonth(outputArray, viewOptions.month);
        }

        if (viewOptions.fromDate != null) {
            assert viewOptions.endDate != null : "There should be a valid end date";
            Sorter.trimFrom(outputArray, viewOptions.fromDate);
            Sorter.trimEnd(outputArray, viewOptions.endDate);
        }

        return outputArray;

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
        case "cat":
            //fallthrough
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
}
