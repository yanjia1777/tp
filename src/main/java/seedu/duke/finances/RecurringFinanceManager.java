package seedu.duke.finances;

import seedu.duke.entries.Entry;
import seedu.duke.entries.Interval;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.parser.Parser;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.parser.ViewOptions;
import seedu.duke.utility.Filter;
import seedu.duke.utility.Ui;
import java.io.File;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RecurringFinanceManager extends FinanceManager {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static final String END_DATE_SEPARATOR = "e/";
    public static final String INTERVAL_SEPARATOR = "i/";
    public ArrayList<Entry> recurringEntryList = new ArrayList<>();
    public static final String RECURRING_FILE_PATH = "data" + File.separator + "MintRecurring.txt";


    public void addEntry(Entry entry) throws MintException {
        recurringEntryList.add(entry);
    }

    @Override
    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags,
                                                  Entry query) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(recurringEntryList);
        RecurringEntry queryToSearch = (RecurringEntry) query;
        for (String tag : tags) {
            switch (tag.trim()) {
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
                filteredList = Filter.filterEntryByCategory(queryToSearch.getCategory().ordinal(), filteredList);
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
        assert recurringEntryList.contains(entry) : "recurringEntryList should contain the entry to delete.";
        logger.log(Level.INFO, "User deleted recurring entry: " + entry);
        recurringEntryList.remove(entry);
    }

    @Override
    public ArrayList<String> editEntry(Entry entry) throws MintException {
        String choice;
        int indexToBeChanged = 0;
        String originalEntryStr = "";
        originalEntryStr = overWriteString((RecurringEntry) entry);
        if (recurringEntryList.contains(entry)) {
            indexToBeChanged = recurringEntryList.indexOf(entry);
            choice = scanFieldsToUpdate();
        } else {
            //                logger.log(Level.INFO, "User entered invalid entry");
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
        }
        ValidityChecker.checkTagsFormatSpacing(choice);
        editSpecifiedEntry(choice, indexToBeChanged, entry);
        String newEntryStr = overWriteString((RecurringEntry) recurringEntryList.get(indexToBeChanged));
        Ui.printOutcomeOfEditAttempt();
        return new ArrayList<>(Arrays.asList(originalEntryStr, newEntryStr));
    }

    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
        try {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            Parser parser = new Parser();
            HashMap<String, String> entryFields = parser.prepareRecurringEntryToAmendForEdit(entry);
            Type type = recurringEntry.getType();
            int count = 0;
            for (String word : choice) {
                assert (word != null);
                if (word.contains(NAME_SEPARATOR)) {
                    String name = nonEmptyNewDescription(word);
                    entryFields.put("name", name);
                    count++;
                } else if (word.contains(DATE_SEPARATOR)) {
                    String dateStr = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("date", dateStr);
                    count++;
                } else if (word.contains(AMOUNT_SEPARATOR)) {
                    String amountStr = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("amount",amountStr);
                    count++;
                } else if (word.contains(CATEGORY_SEPARATOR)) {
                    String catNumStr = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("catNum", catNumStr);
                    count++;
                } else if (word.contains(END_DATE_SEPARATOR)) {
                    String endDateStr = word.substring(word.indexOf(END_DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("endDate", endDateStr);
                    count++;
                } else if (word.contains(INTERVAL_SEPARATOR)) {
                    String intervalStr = word.substring(word.indexOf(INTERVAL_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("interval", intervalStr);
                    count++;
                }
            }
            if (count == 0) {
                throw new MintException("No Valid Fields Entered!");
            }
            setEditedEntry(index, entryFields, type);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    private void setEditedEntry(int index, HashMap<String, String> entryFields, Type type) throws MintException {
        Parser parser = new Parser();
        String name = entryFields.get("name");
        String dateStr = entryFields.get("date");
        String amountStr = entryFields.get("amount");
        String catNumStr = entryFields.get("catNum");
        String intervalStr = entryFields.get("interval");
        String endDateStr = entryFields.get("endDate");

        ValidityChecker.checkValidityOfFieldsInNormalListTxt("expense", name, dateStr, amountStr, catNumStr);
        ValidityChecker.checkValidityOfFieldsInRecurringListTxt(intervalStr, endDateStr);
        RecurringEntry recurringEntry = parser.convertRecurringEntryToRespectiveTypes(entryFields, type);
        recurringEntryList.set(index, recurringEntry);
    }

    public String getStringToUpdate(int index) {
        Entry entry = recurringEntryList.get(index);
        RecurringEntry recurringEntry = (RecurringEntry) entry;
        return recurringEntry.getType() + "|" + recurringEntry.getCategory().ordinal()
                + "|" + recurringEntry.getDate() + "|" + recurringEntry.getName()
                + "|" + recurringEntry.getAmount() + "|" + recurringEntry.getInterval()
                + "|" + recurringEntry.getEndDate();
    }

    public static String overWriteString(RecurringEntry entry) {
        return entry.getType() + "|" + entry.getCategory().ordinal() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount() + "|" + entry.getInterval() + "|" + entry.getEndDate();
    }

    public ArrayList<Entry> getCopyOfRecurringEntryList() {
        ArrayList<Entry> outputArray;
        outputArray = new ArrayList<>(recurringEntryList);
        return outputArray;
    }

    public RecurringEntry createRecurringEntryObject(RecurringEntry entry) {
        if (entry.getType() == Type.Expense) {
            return new RecurringExpense((RecurringExpense) entry);
        } else {
            return new RecurringIncome((RecurringIncome) entry);
        }
    }

    public LocalDate createLocalDateWithYearMonth(YearMonth yearMonth, int day) {
        assert day >= 1 && day <= 31 : "Day should be within 1 - 31";
        String dateToString = yearMonth.toString() + "-" + day;
        return LocalDate.parse(dateToString, Parser.dateFormatter);
    }

    public LocalDate createLocalDateWithIndividualValues(int year, int month, int day) {
        assert month >= 1 && month <= 12 : "Month should be within 1 - 12";
        assert day >= 1 && day <= 31 : "Day should be within 1 - 31";
        String dateToString = year + "-" + month + "-" + day;
        return LocalDate.parse(dateToString, Parser.dateFormatter);
    }

    public void appendEntryByMonth(ArrayList<Entry> entryList, int month, int year) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startYM = YearMonth.from(entry.getDate());
            YearMonth endYM = YearMonth.from(entry.getEndDate());
            YearMonth currentYM = YearMonth.of(year, month);
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryByMonth(entryList, recurringEntry, startYM, endYM, currentYM);
                break;
            case YEAR:
                appendYearlyEntryByMonth(entryList, recurringEntry, startYM, endYM, currentYM);
                break;
            default:
                break;
            }
        }
    }

    void appendMonthlyEntryByMonth(ArrayList<Entry> entryList, RecurringEntry entry, YearMonth startYM,
                                   YearMonth endYM, YearMonth currentYM) {
        boolean isYearMonthBetweenStartAndEnd = startYM.compareTo(currentYM) <= 0
                && currentYM.compareTo(endYM) <= 0;
        if (isYearMonthBetweenStartAndEnd) {
            RecurringEntry newExpense =  createRecurringEntryObject(entry);
            int recurringDay = entry.getDate().getDayOfMonth();
            LocalDate newDate = createLocalDateWithYearMonth(currentYM, recurringDay);
            newExpense.setDate(newDate);
            entryList.add(newExpense);
        }
    }

    void appendYearlyEntryByMonth(ArrayList<Entry> entryList, RecurringEntry entry, YearMonth startYM,
                                  YearMonth endYM, YearMonth currentYM) {
        boolean isSameMonthAsStart = startYM.getMonth() == currentYM.getMonth();
        if (isSameMonthAsStart) {
            appendMonthlyEntryByMonth(entryList, entry, startYM, endYM, currentYM);
        }
        /*
        int startY = startYM.getYear();
        int endY = endYM.getYear();
        int currentY = currentYM.getYear();

        boolean isYearBetweenStartAndEnd = startY <= currentY && currentY <= endY;
        if (isSameMonthAsStart && isYearBetweenStartAndEnd) {
            RecurringEntry newExpense =  createRecurringEntryObject(entry);
            int recurringDay = entry.getDate().getDayOfMonth();
            LocalDate newDate = createLocalDateWithYearMonth(currentYM, recurringDay);
            newExpense.setDate(newDate);
            entryList.add(newExpense);
        }

         */
    }

    public void appendEntryByYear(ArrayList<Entry> entryList, int year) {
        for (Entry entry: recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startYM = YearMonth.from(entry.getDate());
            YearMonth endYM = YearMonth.from(entry.getEndDate());
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryByYear(entryList, recurringEntry, startYM, endYM, year);
                break;
            case YEAR:
                appendYearlyEntryByYear(entryList, recurringEntry, startYM, endYM, year);
                break;
            default:
                break;
            }
        }
    }

    void appendMonthlyEntryByYear(ArrayList<Entry> entryList, RecurringEntry entry, YearMonth startYM,
            YearMonth endYM, int currentY) {
        YearMonth iteratorYM = YearMonth.of(currentY, Month.JANUARY);
        YearMonth endLoopYM = YearMonth.of(currentY, Month.DECEMBER);

        while (iteratorYM.compareTo(endLoopYM) <= 0) {
            appendMonthlyEntryByMonth(entryList, entry, startYM, endYM, iteratorYM);
            /*
            boolean isBetweenRecurringPeriod = iteratorYM.compareTo(startYM) >= 0
                    && iteratorYM.compareTo(endYM) <= 0;
            if (isBetweenRecurringPeriod) {
                RecurringEntry newExpense =  createRecurringEntryObject(entry);
                int recurringDay = entry.getDate().getDayOfMonth();
                LocalDate newDate = createLocalDateWithYearMonth(iteratorYM,recurringDay);
                newExpense.setDate(newDate);
                entryList.add(newExpense);
            }

             */
            iteratorYM = iteratorYM.plusMonths(1);
        }
    }

    void appendYearlyEntryByYear(ArrayList<Entry> entryList, RecurringEntry entry, YearMonth startYM,
            YearMonth endYM, int currentY) {
        YearMonth currentYM = YearMonth.of(currentY, startYM.getMonthValue());
        appendYearlyEntryByMonth(entryList, entry, startYM, endYM, currentYM);
    }

    public void appendEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            LocalDate startDate, LocalDate endDate) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryBetweenTwoDates(entryList, rawRecurringList, recurringEntry, startDate, endDate);
                break;
            case YEAR:
                appendYearlyEntryBetweenTwoDates(entryList, rawRecurringList, recurringEntry, startDate, endDate);
                break;
            default:
                break;
            }
        }
    }

    void appendMonthlyEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, LocalDate startDate, LocalDate endDate) {
        YearMonth startRecurringYM = YearMonth.from(entry.getDate());
        YearMonth endRecurringYM = YearMonth.from(entry.getEndDate());
        YearMonth endYM = YearMonth.from(endDate);
        YearMonth iteratorYM = startRecurringYM;
        YearMonth endLoopYM = endYM.isBefore(endRecurringYM) ? endYM : endRecurringYM;
        boolean isAdded = false;

        while (iteratorYM.compareTo(endLoopYM) <= 0) {
            int recurringDay = entry.getDate().getDayOfMonth();
            LocalDate currentDate = createLocalDateWithYearMonth(iteratorYM, recurringDay);
            boolean isAfterStart = currentDate.compareTo(startDate) >= 0;
            boolean isBeforeEnd = currentDate.compareTo(endDate) <= 0;

            if (isAfterStart && isBeforeEnd) {
                RecurringEntry newExpense =  createRecurringEntryObject(entry);
                newExpense.setDate(currentDate);
                entryList.add(newExpense);
                isAdded = true;
            }
            iteratorYM = iteratorYM.plusMonths(1);
        }

        if (isAdded) {
            rawRecurringList.add(entry);
        }
    }

    void appendYearlyEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, LocalDate startDate, LocalDate endDate) {
        LocalDate startRecurringDate = entry.getDate();
        int startRecurringYear = startRecurringDate.getYear();
        int startRecurringMonth = startRecurringDate.getMonthValue();
        int startRecurringDay = startRecurringDate.getDayOfMonth();
        int endRecurringYear = entry.getEndDate().getYear();
        int endYear = endDate.getYear();
        boolean isAdded = false;

        int effectiveEndYear = Math.min(endRecurringYear, endYear);
        for (int i = startRecurringYear; i <= effectiveEndYear; i++) {
            LocalDate currentDate = createLocalDateWithIndividualValues(i, startRecurringMonth, startRecurringDay);
            boolean isAfterStart = currentDate.compareTo(startDate) >= 0;
            boolean isBeforeEnd = currentDate.compareTo(endDate) <= 0;

            if (isAfterStart && isBeforeEnd) {
                RecurringEntry newExpense = createRecurringEntryObject(entry);
                newExpense.setDate(currentDate);
                entryList.add(newExpense);
                isAdded = true;
            }
        }

        if (isAdded) {
            rawRecurringList.add(entry);
        }
    }

    public ArrayList<Entry> appendEntryForView(ViewOptions viewOptions, ArrayList<Entry> entryList,
            ArrayList<Entry> recurringOnlyList) {
        if (viewOptions.fromDate != null) {
            appendEntryBetweenTwoDates(entryList, recurringOnlyList, viewOptions.fromDate, viewOptions.endDate);
        } else if (viewOptions.isViewAll) {
            ArrayList<Entry> dummyList = new ArrayList<>();
            appendAllEntry(entryList, dummyList);
            recurringOnlyList.addAll(recurringEntryList);
        } else {
            if (viewOptions.month == null) {
                appendEntryByYear(entryList, viewOptions.year);
                viewByYear(viewOptions, recurringOnlyList);

            } else {
                appendEntryByMonth(entryList, viewOptions.month.getValue(), viewOptions.year);
                viewByMonth(viewOptions, recurringOnlyList);
            }
        }
        return entryList;
    }

    public void appendAllEntry(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList) {
        LocalDate earliestDate = LocalDate.now();
        for (Entry recurringExpense : recurringEntryList) {
            if (recurringExpense.getDate().isBefore(earliestDate)) {
                earliestDate = recurringExpense.getDate();
            }
        }
        appendEntryBetweenTwoDates(entryList, rawRecurringList, earliestDate, LocalDate.now());
    }

    public void viewByMonth(ViewOptions viewOptions, ArrayList<Entry> entryList) {
        YearMonth currentYM = YearMonth.of(viewOptions.year, viewOptions.month);
        for (Entry entry : recurringEntryList) {
            YearMonth startYM = YearMonth.from(entry.getDate());
            YearMonth endYM = YearMonth.from(entry.getEndDate());
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            boolean isSameMonthAsStart = startYM.getMonth() == currentYM.getMonth();
            boolean isBetween = startYM.compareTo(currentYM) <= 0 && currentYM.compareTo(endYM) <= 0;
            Interval interval = entry.getInterval();
            
            switch (interval) {
            case MONTH:
                if (isBetween) {
                    entryList.add(recurringEntry);
                }
                break;
            case YEAR:
                if (isSameMonthAsStart && isBetween) {
                    entryList.add(recurringEntry);
                }
                break;
            default:
                break;
            }
        }
    }

    public void viewByYear(ViewOptions viewOptions, ArrayList<Entry> entryList) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            int startRecurringYear = recurringEntry.getDate().getYear();
            int endRecurringYear = recurringEntry.getEndDate().getYear();
            int currYear = viewOptions.year;
            boolean isBetween = currYear >= startRecurringYear && currYear <= endRecurringYear;
            if (isBetween) {
                entryList.add(recurringEntry);
            }
        }
    }

    public void viewBetweenTwoDates(ViewOptions viewOptions, ArrayList<Entry> entryList) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            LocalDate startRecurringDate = recurringEntry.getDate();
            LocalDate endRecurringDate = recurringEntry.getEndDate();
            boolean isFromDateBetween = startRecurringDate.compareTo(viewOptions.fromDate) <= 0;
            boolean isEndDateBetween = endRecurringDate.compareTo(viewOptions.endDate) >= 0;
            if (isFromDateBetween || isEndDateBetween) {
                entryList.add(recurringEntry);
            }
        }
    }

    public void deleteAll() {
        recurringEntryList.clear();
    }
}
