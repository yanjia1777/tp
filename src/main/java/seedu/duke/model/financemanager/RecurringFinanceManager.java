package seedu.duke.model.financemanager;

import seedu.duke.model.entries.Entry;
import seedu.duke.model.entries.RecurringEntry;
import seedu.duke.model.entries.RecurringExpense;
import seedu.duke.model.entries.RecurringIncome;
import seedu.duke.model.entries.Type;
import seedu.duke.utility.MintException;
import seedu.duke.logic.parser.Parser;
import seedu.duke.logic.parser.ValidityChecker;
import seedu.duke.logic.parser.ViewOptions;
import seedu.duke.utility.Filter;
import seedu.duke.ui.Ui;
import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    //@@author pos0414
    /**
     * Filters the recurring entries that matches the fields that user queried.
     * @param tags List of tags that user has queried.
     * @param query Entry object that contains the details of the query that user has made.
     * @return List of matching recurring entries
     * @throws MintException If tag given is invalid
     */
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

    /**
     * Deletes the recurring entry in the recurringEntryList. To reach this point all validity checks and the existence
     * of the recurring entry inside the recurringEntryList have been checked.
     * Thus assume the entry exists in the list.
     * @param entry Entry to be deleted.
     */
    @Override
    public void deleteEntry(Entry entry) {
        assert recurringEntryList.contains(entry) : "recurringEntryList should contain the entry to delete.";
        logger.log(Level.INFO, "User deleted recurring entry: " + entry);
        recurringEntryList.remove(entry);
    }

    //@@author Yitching
    /**
     * Calls all the methods required for edit.
     *
     * @param entry Entry type variable, casted to RecurringEntry type, that contains all the attributes of the entry.
     *
     * @return returns an ArrayList containing the string to be overwritten in the external text file and the new
     *     string to overwrite the old string in the external text file.
     */
    @Override
    public ArrayList<String> editEntry(Entry entry) throws MintException {
        String choice;
        int indexToBeChanged;
        String originalEntryStr;
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

    /**
     * Splits user input into the respective fields via tags.
     *
     * @param index the index of the recurringEntryList to be edited.
     * @param choice user input containing the fields user wishes to edit.
     * @param entry Entry type variable, casted to RecurringEntry, that contains all the attributes of the \
     *     recurring expense.
     */
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

    /**
     * Checks validity of the new entry to be used to overwrite the old entry.
     *
     * @param index the index of the recurringEntryList to be edited.
     * @param entryFields HashMap containing all the String type attributes.
     * @param type refers to whether it is an expense or an income.
     */
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
        ValidityChecker.checkInvalidEndDate(endDateStr, dateStr);
        RecurringEntry recurringEntry = parser.convertRecurringEntryToRespectiveTypes(entryFields, type);
        recurringEntryList.set(index, recurringEntry);
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

    //@@author pos0414
    /**
     * Create LocalDate when YearMonth and day is given. If the day does not exist in that specific year and month,
     * LocalDate created will have a rounded down day.
     * Only valid year, month, and day are given.
     * @param yearMonth YearMonth representing the year and month
     * @param day Integer value of the day
     * @return LocalDate created from yearMonth and day
     */
    public LocalDate createLocalDateWithYearMonth(YearMonth yearMonth, int day) {
        assert day >= 1 && day <= 31 : "Day should be within 1 - 31";
        String dateToString = yearMonth.toString() + "-" + day;
        return LocalDate.parse(dateToString, Parser.dateFormatter);
    }

    /**
     * Create LocalDate when year, month, and days are given. If the day does not exist in that specific year
     * and month, LocalDate created will have a rounded down day.
     * Only valid year, month, and day are given.
     * @param year Integer value of the year
     * @param month Integer value of the month
     * @param day Integer value of the day
     * @return LocalDate created from the year, month, and day
     */
    public LocalDate createLocalDateWithIndividualValues(int year, int month, int day) {
        assert month >= 1 && month <= 12 : "Month should be within 1 - 12";
        assert day >= 1 && day <= 31 : "Day should be within 1 - 31";
        String dateToString = year + "-" + month + "-" + day;
        return LocalDate.parse(dateToString, Parser.dateFormatter);
    }

    /**
     * Checks if each recurring entry in the recurringEntryList recurs for a specific year and month. Creates and
     * appends the recurring entries with new date that it has recurred if they recur on that year and month,
     * based on the recurring period and interval. Original entry is added to rawRecurringList if that
     * entry has recurred on this year and month.
     * @param entryList List to append the recurring entries
     *
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param month Month the user wants to view
     * @param year Year the user wants to view
     */
    public void appendEntryByMonth(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            int month, int year) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startYM = YearMonth.from(entry.getDate());
            YearMonth currentYM = YearMonth.of(year, month);
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryByMonth(entryList, rawRecurringList, recurringEntry, currentYM);
                break;
            case YEAR:
                appendYearlyEntryByMonth(entryList, rawRecurringList, recurringEntry, startYM, currentYM);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Checks if a given monthly recurring entry has recurred on a specific year month, based on the recurring
     * start date and end date. The date that the entry has recurred is set as the new date for
     * the recurring entry, and added to the entryList. Original entry is added to rawRecurringList if that
     * entry has recurred on this year and month.
     * @param entryList List to append the new-dated recurring entry if it has recurred
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param entry Recurring entry to be checked
     * @param currentYM YearMonth to be checked if the entry has recurred on that year month
     */
    public void appendMonthlyEntryByMonth(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, YearMonth currentYM) {
        LocalDate startDate = entry.getDate();
        LocalDate endDate = entry.getEndDate();
        int recurringDay = startDate.getDayOfMonth();
        LocalDate currentDate = createLocalDateWithYearMonth(currentYM, recurringDay);
        boolean isBetween = startDate.compareTo(currentDate) <= 0
                && currentDate.compareTo(endDate) <= 0;
        if (isBetween) {
            RecurringEntry newExpense =  createRecurringEntryObject(entry);
            newExpense.setDate(currentDate);
            entryList.add(newExpense);
            rawRecurringList.add(entry);
        }
    }

    /**
     * Checks if a given yearly recurring entry has recurred on a specific year month, based on the recurring
     * start date and end date. The date that the entry has recurred is set as the new date for
     * the recurring entry, and added to the entryList. Original entry is added to rawRecurringList if that
     * entry has recurred on this year and month.
     * @param entryList List to append the new-dated recurring entry if it has recurred
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param entry Recurring entry to be checked
     * @param startYM YearMonth of the start date of the recurring entry
     * @param currentYM YearMonth to be checked if the entry has recurred on that year month
     */
    public void appendYearlyEntryByMonth(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, YearMonth startYM, YearMonth currentYM) {
        boolean isSameMonthAsStart = startYM.getMonth() == currentYM.getMonth();
        if (isSameMonthAsStart) {
            appendMonthlyEntryByMonth(entryList, rawRecurringList, entry, currentYM);
        }
    }

    /**
     * Creates and appends the recurring entries to a list with new dates that they have recurred if they do recur
     * on that year, based on the recurring period and interval, for each recurring entry in the recurringEntryList.
     * Monthly recurring entries are added for each month in that year it has recurred on that year and month. Yearly
     * recurring entry is added once for that year if it has recurred on that year. Original entry is added to
     * rawRecurringList if that entry has recurred on that year.
     * @param entryList List to append the recurring entries
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param year Year the user wants to view
     */
    public void appendEntryByYear(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList, int year) {
        for (Entry entry: recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startYM = YearMonth.from(entry.getDate());
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryByYear(entryList, rawRecurringList, recurringEntry, year);
                break;
            case YEAR:
                appendYearlyEntryByYear(entryList, rawRecurringList, recurringEntry, startYM, year);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Checks if a given monthly recurring entry has recurred on each month of the year given, based on the recurring
     * start date and end date. The date that the entry has recurred is set as the new date for
     * the recurring entry, and added to the entryList. Original entry is added to rawRecurringList if that
     * entry has recurred on this year.
     * @param entryList List to append the new-dated recurring entry if it has recurred
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param entry Recurring entry to be checked
     * @param currentY Year to be checked if the entry has recurred on that year's months
     */
    public void appendMonthlyEntryByYear(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, int currentY) {
        YearMonth iteratorYM = YearMonth.of(currentY, Month.JANUARY);
        YearMonth endLoopYM = YearMonth.of(currentY, Month.DECEMBER);
        ArrayList<Entry> dummyList = new ArrayList<>();
        boolean isAdded = false;
        while (iteratorYM.compareTo(endLoopYM) <= 0) {
            appendMonthlyEntryByMonth(entryList, dummyList, entry, iteratorYM);
            if (dummyList.size() > 0) {
                isAdded = true;
                dummyList.remove(0);
            }
            iteratorYM = iteratorYM.plusMonths(1);
        }

        if (isAdded) {
            rawRecurringList.add(entry);
        }
    }

    /**
     * Checks if a given yearly recurring entry has recurred on the year given, based on the recurring
     * start date and end date. The date that the entry has recurred is set as the new date for
     * the recurring entry, and added to the entryList. Original entry is added to rawRecurringList if that
     * entry has recurred on this month.
     * @param entryList List to append the new-dated recurring entry if it has recurred
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param entry Recurring entry to be checked
     * @param startYM YearMonth of the start date of the recurring entry
     * @param currentY Year to be checked if the entry has recurred on that year
     */
    public void appendYearlyEntryByYear(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, YearMonth startYM,
            int currentY) {
        YearMonth currentYM = YearMonth.of(currentY, startYM.getMonthValue());
        appendYearlyEntryByMonth(entryList, rawRecurringList, entry, startYM, currentYM);
    }

    /**
     * Creates and appends the recurring entries to a list with new
     * dates that they have recurred if they do recur between the two dates, based on the
     * recurring period and interval, for each recurring entry in the recurringEntryList. Monthly recurring
     * entries are added for each month between the two dates that it has recurred. Yearly recurring entries are added
     * for each year between the two dates that it has recurred. Original entry is added to rawRecurringList if that
     * entry has recurred between the two dates.
     * @param entryList List to append the recurring entries
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param startDate Starting date of the period that the user wants to view
     * @param endDate Ending date of the period that the user wants to view
     */
    public void appendEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            LocalDate startDate, LocalDate endDate) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            LocalDate startRecurringDate = entry.getDate();
            LocalDate endRecurringDate = entry.getEndDate();
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryBetweenTwoDates(entryList, rawRecurringList, recurringEntry,
                        startDate, endDate, startRecurringDate, endRecurringDate);
                break;
            case YEAR:
                appendYearlyEntryBetweenTwoDates(entryList, rawRecurringList, recurringEntry,
                        startDate, endDate, startRecurringDate, endRecurringDate);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Checks if a given monthly recurring entry has recurred on each month between the two dates given, based on
     * the recurring start date and end date. The date that the entry has recurred is set as the new date for
     * the recurring entry, and added to the entryList. Original entry is added to rawRecurringList if that
     * entry has recurred on this year.
     * @param entryList  List to append the new-dated recurring entry if it has recurred
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param entry Recurring entry to be checked
     * @param startDate Starting date of the period that the user wants to view
     * @param endDate Ending date of the period that the user wants to view
     * @param startRecurringDate Start date of the recurring entry
     * @param endRecurringDate End date of the recurring entry
     */
    public void appendMonthlyEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, LocalDate startDate, LocalDate endDate,
            LocalDate startRecurringDate, LocalDate endRecurringDate) {

        YearMonth endRecurringYM = YearMonth.from(endRecurringDate);
        YearMonth endYM = YearMonth.from(endDate);
        YearMonth iteratorYM = YearMonth.from(startRecurringDate);
        YearMonth endLoopYM = endYM.isBefore(endRecurringYM) ? endYM : endRecurringYM;
        int recurringDay = entry.getDate().getDayOfMonth();
        boolean isAdded = false;

        while (iteratorYM.compareTo(endLoopYM) <= 0) {
            LocalDate currentDate = createLocalDateWithYearMonth(iteratorYM, recurringDay);
            boolean isBetween = isBetweenQueryAndRecurring(currentDate, startDate, endDate,
                    startRecurringDate, endRecurringDate);
            if (isBetween) {
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

    /**
     * Checks if a given LocalDate is between the recurring period (startRecurringDate to endRecurringDate) as well as
     * between the query period (startDate to endDate)
     * @param currentDate LocalDate to be checked if ii is between the two periods
     * @param startDate Starting date of the query period
     * @param endDate Ending date of the query period
     * @param startRecurringDate Start date of the recurring entry
     * @param endRecurringDate End date of the recurring entry
     * @return True if it is between both periods, false if it isn't
     */
    public boolean isBetweenQueryAndRecurring(LocalDate currentDate, LocalDate startDate, LocalDate endDate,
            LocalDate startRecurringDate, LocalDate endRecurringDate) {
        boolean isBetweenQuery = startDate.compareTo(currentDate) <= 0
                && currentDate.compareTo(endDate) <= 0;
        boolean isBetweenRecurringPeriod = startRecurringDate.compareTo(currentDate) <= 0
                && currentDate.compareTo(endRecurringDate) <= 0;
        return isBetweenQuery && isBetweenRecurringPeriod;
    }

    /**
     * Checks if a given yearly recurring entry has recurred on each year between the two dates given, based on
     * the recurring start date and end date. The date that the entry has recurred is set as the new date for
     * the recurring entry, and added to the entryList. Original entry is added to rawRecurringList if that
     * entry has recurred on this year.
     * @param entryList List to append the new-dated recurring entry if it has recurred
     * @param rawRecurringList List that wants the original form of recurring entries that were appended
     * @param entry Recurring entry to be checked
     * @param startDate Starting date of the period that the user wants to view
     * @param endDate Ending date of the period that the user wants to view
     * @param startRecurringDate Start date of the recurring entry
     * @param endRecurringDate End date of the recurring entry
     */
    public void appendYearlyEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, LocalDate startDate, LocalDate endDate,
            LocalDate startRecurringDate, LocalDate endRecurringDate) {

        int startRecurringYear = startRecurringDate.getYear();
        int startRecurringMonth = startRecurringDate.getMonthValue();
        int startRecurringDay = startRecurringDate.getDayOfMonth();
        int endRecurringYear = entry.getEndDate().getYear();
        int endYear = endDate.getYear();
        boolean isAdded = false;

        int effectiveEndYear = Math.min(endRecurringYear, endYear);
        for (int i = startRecurringYear; i <= effectiveEndYear; i++) {
            LocalDate currentDate = createLocalDateWithIndividualValues(i, startRecurringMonth, startRecurringDay);
            boolean isBetween = isBetweenQueryAndRecurring(currentDate, startDate, endDate,
                    startRecurringDate, endRecurringDate);

            if (isBetween) {
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

    /**
     * Given the date options for viewing the entries, it creates and appends the recurring entries to a list with new
     * dates that they have recurred if they do recur during/on years/months of the date option, for each recurring
     * entry in the recurringEntryList. Original recurring entry is added to rawRecurringList if that entry has
     * recurred on that date option.
     * @param viewOptions ViewOptions that contains the date options the user want to view the entries by
     * @param entryList List to append the new-dated recurring entry if it has recurred
     * @param recurringOnlyList  List that wants the original form of recurring entries that were appended
     * @return The appended entryList
     */
    public ArrayList<Entry> appendEntryForView(ViewOptions viewOptions, ArrayList<Entry> entryList,
            ArrayList<Entry> recurringOnlyList) {
        if (viewOptions.fromDate != null) {
            appendEntryBetweenTwoDates(entryList, recurringOnlyList, viewOptions.fromDate, viewOptions.endDate);
        } else if (viewOptions.isViewAll) {
            appendAllEntry(entryList);
            recurringOnlyList.addAll(recurringEntryList);
        } else {
            if (viewOptions.month == null) {
                appendEntryByYear(entryList, recurringOnlyList, viewOptions.year);
            } else {
                appendEntryByMonth(entryList, recurringOnlyList, viewOptions.month.getValue(), viewOptions.year);
            }
        }
        return entryList;
    }

    /**
     * Creates and appends the recurring entries to a list with new
     * dates that they have recurred if they do recur from the past until today, based on the
     * recurring period and interval, for each recurring entry in the recurringEntryList. Monthly recurring
     * entries are added for each month from the start date of that recurring entry. Yearly recurring entry is added
     * for each year between the two dates that it has recurred. Original entry is added to rawRecurringList if that
     * recurring entry has recurred from the past until today.
     * @param entryList List to append the new-dated recurring entry if it has recurred
     */
    public void appendAllEntry(ArrayList<Entry> entryList) {
        LocalDate earliestDate = LocalDate.now();
        for (Entry recurringExpense : recurringEntryList) {
            if (recurringExpense.getDate().isBefore(earliestDate)) {
                earliestDate = recurringExpense.getDate();
            }
        }
        ArrayList<Entry> dummyList = new ArrayList<>();
        appendEntryBetweenTwoDates(entryList, dummyList, earliestDate, LocalDate.now());
    }

    //@@author yanjia1777
    public void deleteAll() {
        recurringEntryList.clear();
    }
}
