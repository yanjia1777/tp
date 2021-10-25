package seedu.duke.finances;

import seedu.duke.entries.Entry;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.entries.Type;
import seedu.duke.entries.Interval;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Filter;
import seedu.duke.utility.Sorter;
import seedu.duke.utility.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class RecurringFinanceManager extends FinanceManager {
    public static final String END_DATE_SEPARATOR = "e/";
    public static final String INTERVAL_SEPARATOR = "i/";
    public ArrayList<Entry> recurringEntryList = new ArrayList<>();
    public static final String RECURRING_FILE_PATH = "data" + File.separator + "MintRecurring.txt";

    public void addEntry(Entry entry) throws MintException {
        recurringEntryList.add(entry);
    }

    @Override
    public Entry chooseEntryByKeywords(ArrayList<String> tags, boolean isDelete,
                                       Entry query) throws MintException {
        ArrayList<Entry> filteredList = filterEntryByKeywords(tags, query);
        RecurringEntry entry = null;
        if (filteredList.size() == 0) {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        } else if (filteredList.size() == 1) {
            RecurringEntry onlyExpense = (RecurringEntry) filteredList.get(0);
            if (Ui.isConfirmedToDeleteOrEdit(onlyExpense, isDelete)) {
                entry = onlyExpense;
            } else {
                throw new MintException("Ok. I have cancelled the process.");
            }
            return entry;
        }

        Ui.viewGivenList(filteredList);
        try {
            int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
            if (index >= 0) {
                entry = (RecurringEntry) filteredList.get(index);
            } else {
                throw new MintException("Ok. I have cancelled the process.");
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return entry;
    }

    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags,
                                                  Entry query) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(recurringEntryList);
        RecurringEntry queryToSearch = (RecurringEntry) query;
        for (String tag : tags) {
            switch (tag) {
            case "n/":
                filteredList = Filter.filterEntryByName(queryToSearch.getName(), filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(queryToSearch.getDate(), filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(queryToSearch.getAmount(), filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCategory(queryToSearch.getCategory(), filteredList);
                break;
            case "i/":
                filteredList = Filter.filterEntryByInterval(queryToSearch.getInterval().label, filteredList);
                break;
            case "e/":
                filteredList = Filter.filterEntryByEndDate(queryToSearch.getEndDate(), filteredList);
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
        recurringEntryList.remove(entry);
    }

    @Override
    public ArrayList<String> editEntry(Entry entry) throws MintException {
        String choice;
        int indexToBeChanged = 0;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;
        String originalEntryStr = "";
        try {
            originalEntryStr = overWriteString((RecurringEntry) entry);
            if (recurringEntryList.contains(entry)) {
                indexToBeChanged = recurringEntryList.indexOf(entry);
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
        String newEntryStr = overWriteString((RecurringEntry) recurringEntryList.get(indexToBeChanged));
        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
        return new ArrayList<>(Arrays.asList(originalEntryStr, newEntryStr));
    }

    private Boolean isEditSuccessful(int indexToBeChanged, String originalExpense) {
        String newExpense = recurringEntryList.get(indexToBeChanged).toString();
        return !originalExpense.equals(newExpense);
    }

    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
        try {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            String name = recurringEntry.getName();
            LocalDate date = recurringEntry.getDate();
            double amount = recurringEntry.getAmount();
            LocalDate endDate = recurringEntry.getEndDate();
            Interval interval = recurringEntry.getInterval();
            Enum category = recurringEntry.getCategory();
            int count = 0;
            for (String word : choice) {
                assert (word != null);
                if (word.contains(NAME_SEPARATOR)) {
                    name = nonEmptyNewDescription(word);
                    count++;
                }
                if (word.contains(DATE_SEPARATOR)) {
                    String dateStr = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    date = LocalDate.parse(dateStr, ValidityChecker.dateFormatter);
                    count++;
                }
                if (word.contains(AMOUNT_SEPARATOR)) {
                    String amountStr = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    amount = Double.parseDouble(amountStr);
                    count++;
                }
                if (word.contains(CATEGORY_SEPARATOR)) {
                    String catNumStr = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    int pos = Integer.parseInt(catNumStr);
                    category = ExpenseCategory.values()[pos];
                    count++;
                }
                if (word.contains(END_DATE_SEPARATOR)) {
                    String endDateStr = word.substring(word.indexOf(END_DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    endDate = LocalDate.parse(endDateStr, ValidityChecker.dateFormatter);
                    count++;
                }
                if (word.contains(INTERVAL_SEPARATOR)) {
                    String intervalStr = word.substring(word.indexOf(INTERVAL_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    interval = Interval.determineInterval(intervalStr);
                    count++;
                }
            }
            if (count == 0) {
                throw new MintException("No Valid Fields Entered!");
            }
            if (recurringEntry.getType() == Type.Expense) {
                recurringEntryList.set(index, new RecurringExpense(name, date, amount, (ExpenseCategory) category,
                        interval, endDate));
            } else {
                recurringEntryList.set(index, new RecurringIncome(name, date, amount, (IncomeCategory) category,
                        interval, endDate));
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public String getStringToUpdate(int index) {
        Entry entry = recurringEntryList.get(index);
        RecurringEntry recurringEntry = (RecurringEntry) entry;
        return recurringEntry.getType() + "|" + recurringEntry.getCategory().ordinal()
                + "|" + recurringEntry.getDate() + "|" + recurringEntry.getName()
                + "|" + recurringEntry.getAmount() + "|" + recurringEntry.getInterval()
                + "|" + recurringEntry.getEndDate();
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
        //add case for endDate
        default:
            throw new MintException(MintException.ERROR_INVALID_COMMAND);
        }
    }

    public static String overWriteString(RecurringEntry entry) {
        return entry.getType() + "|" + entry.getCategory().ordinal() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount() + "|" + entry.getInterval() + "|" + entry.getEndDate();
    }

    public RecurringEntry createRecurringEntryObject(RecurringEntry entry) {
        if (entry.getType() == Type.Expense) {
            return new RecurringExpense((RecurringExpense) entry);
        } else {
            return new RecurringIncome((RecurringIncome) entry);
        }
    }

    public void viewRecurringEntryByMonth(ArrayList<Entry> expenseList, int month, int year) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startYM = YearMonth.from(recurringEntry.getDate());
            YearMonth endYM = YearMonth.from(recurringEntry.getEndDate());
            YearMonth currentYM = YearMonth.of(year, month);
            int startY = recurringEntry.getDate().getYear();
            int endY = recurringEntry.getEndDate().getYear();

            switch (recurringEntry.getInterval()) {
            case MONTH:
                boolean isYearMonthBetweenStartAndEnd = startYM.compareTo(currentYM) <= 0
                        && currentYM.compareTo(endYM) <= 0;
                if (isYearMonthBetweenStartAndEnd) {
                    RecurringEntry newExpense =  createRecurringEntryObject(recurringEntry);
                    newExpense.setDate(currentYM.atDay(recurringEntry.getDate().getDayOfMonth()));
                    expenseList.add(newExpense);
                }
                break;
            case YEAR:
                boolean isSameMonthAsStart = startYM.getMonth() == currentYM.getMonth();
                boolean isYearBetweenStartAndEnd = startY <= year && year <= endY;
                if (isSameMonthAsStart && isYearBetweenStartAndEnd) {
                    RecurringEntry newExpense =  createRecurringEntryObject(recurringEntry);
                    newExpense.setDate(currentYM.atDay(recurringEntry.getDate().getDayOfMonth()));
                    expenseList.add(newExpense);
                }
                break;
            default:
                break;
            }
        }
    }

    public void viewRecurringExpenseByYear(ArrayList<Entry> expenseList, int year) {
        for (Entry entry: recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startRecurringYM = YearMonth.from(recurringEntry.getDate());
            YearMonth endRecurringYM = YearMonth.from(recurringEntry.getEndDate());
            int startY = recurringEntry.getDate().getYear();
            int endY = recurringEntry.getEndDate().getYear();

            switch (recurringEntry.getInterval()) {
            case MONTH:
                YearMonth iteratorYM = YearMonth.of(year, Month.JANUARY);
                YearMonth endLoopYM = YearMonth.of(year, Month.DECEMBER);
                while (iteratorYM.compareTo(endLoopYM) <= 0) {
                    boolean isBetweenRecurringPeriod = iteratorYM.compareTo(startRecurringYM) >= 0
                            && iteratorYM.compareTo(endRecurringYM) <= 0;
                    if (isBetweenRecurringPeriod) {
                        RecurringEntry newExpense =  createRecurringEntryObject(recurringEntry);
                        newExpense.setDate(iteratorYM.atDay(recurringEntry.getDate().getDayOfMonth()));
                        expenseList.add(newExpense);
                    }
                    iteratorYM = iteratorYM.plusMonths(1);
                }
                break;
            case YEAR:
                boolean isYearBetweenStartAndEnd = startY <= year && year <= endY;
                if (isYearBetweenStartAndEnd) {
                    YearMonth billYM = YearMonth.of(year, startRecurringYM.getMonthValue());
                    recurringEntry.setDate(billYM.atDay(recurringEntry.getDate().getDayOfMonth()));
                    expenseList.add(recurringEntry);
                }
                break;
            default:
                break;
            }
        }
    }

    public void viewRecurringExpenseBetweenTwoDates(ArrayList<Entry> expenseList, LocalDate startDate,
                                                    LocalDate endDate) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            LocalDate startRecurringDate = recurringEntry.getDate();
            int startRecurringYear = recurringEntry.getDate().getYear();
            int endRecurringYear = recurringEntry.getEndDate().getYear();
            int endYear = endDate.getYear();
            YearMonth startRecurringYM = YearMonth.from(recurringEntry.getDate());
            YearMonth endRecurringYM = YearMonth.from(recurringEntry.getEndDate());
            YearMonth endYM = YearMonth.from(endDate);

            switch (recurringEntry.getInterval()) {
            case MONTH:
                YearMonth iteratorYM = startRecurringYM;
                YearMonth endLoopYM = endYM.isBefore(endRecurringYM) ? endYM : endRecurringYM;
                while (iteratorYM.compareTo(endLoopYM) <= 0) {
                    LocalDate currentDate = iteratorYM.atDay(recurringEntry.getDate().getDayOfMonth());
                    if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                        RecurringEntry newExpense =  createRecurringEntryObject(recurringEntry);
                        newExpense.setDate(iteratorYM.atDay(recurringEntry.getDate().getDayOfMonth()));
                        expenseList.add(newExpense);
                    }
                    iteratorYM = iteratorYM.plusMonths(1);
                }
                break;
            case YEAR:
                int effectiveEndYear = Math.min(endRecurringYear, endYear);
                for (int i = startRecurringYear; i <= effectiveEndYear; i++) {
                    LocalDate currentDate = LocalDate.of(i, startRecurringDate.getMonthValue(),
                            startRecurringDate.getDayOfMonth());
                    if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                        RecurringEntry newExpense =  createRecurringEntryObject(recurringEntry);
                        newExpense.setDate(currentDate);
                        expenseList.add(newExpense);
                    }
                }
                break;
            default:
                break;
            }
        }
    }

    public void viewAllRecurringExpense(ArrayList<Entry> expenseList) {
        LocalDate earliestDate = LocalDate.now();
        for (Entry recurringExpense : recurringEntryList) {
            if (recurringExpense.getDate().isBefore(earliestDate)) {
                earliestDate = recurringExpense.getDate();
            }
        }
        viewRecurringExpenseBetweenTwoDates(expenseList, earliestDate, LocalDate.now());
    }
}