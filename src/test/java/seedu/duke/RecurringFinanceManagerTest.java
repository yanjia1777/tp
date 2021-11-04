package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Interval;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.exception.MintException;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import static seedu.duke.parser.ValidityChecker.dateFormatter;

class RecurringFinanceManagerTest {
    RecurringFinanceManager financeManager = new RecurringFinanceManager();
    ArrayList<Entry> entryList = financeManager.recurringEntryList;

    LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
    LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
    LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
    LocalDate endDate1 = LocalDate.parse("2022-10-29", dateFormatter);
    LocalDate endDate2 = LocalDate.parse("2023-01-11", dateFormatter);

    @Test
    public void deleteEntry_RecurringExpense_success() {
        String name = "Cheese";
        LocalDate date = LocalDate.parse("2021-12-23", dateFormatter);
        Double amount = 15.5;
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2022-12-23", dateFormatter);

        entryList.add(new RecurringExpense(name, date, amount, category, interval, endDate));
        RecurringExpense query = new RecurringExpense(name, date, amount, category, interval, endDate);
        assertTrue(entryList.contains(query));
        financeManager.deleteEntry(query);
        assertFalse(entryList.contains(query));
    }

    @Test
    public void deleteEntry_RecurringIncome_success() {
        String name = "Salary";
        LocalDate date = LocalDate.parse("2021-12-23", dateFormatter);
        Double amount = 10000.0;
        IncomeCategory category = IncomeCategory.SALARY;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2022-12-23", dateFormatter);

        entryList.add(new RecurringIncome(name, date, amount, category,interval, endDate));
        RecurringIncome query = new RecurringIncome(name, date, amount, category, interval, endDate);
        assertTrue(entryList.contains(query));
        financeManager.deleteEntry(query);
        assertFalse(entryList.contains(query));
    }

    @Test
    public void filterEntryByKeywords_queryFullName_oneMatchingResult() {
        RecurringIncome targetEntry = new RecurringIncome(
                "Pear", date1, 3.9, IncomeCategory.SALARY, Interval.MONTH, endDate1);
        RecurringExpense dummyEntry = new RecurringExpense(
                "Apple", date2, 5.0, ExpenseCategory.FOOD, Interval.MONTH, endDate2);

        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);

        RecurringExpense query = new RecurringExpense(
                "Pear", date3, 5.0, ExpenseCategory.FOOD, Interval.YEAR, endDate2);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryFullName_multipleMatchingResult() {
        RecurringExpense targetEntry1 = new RecurringExpense(
                "pork", date1, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        RecurringIncome targetEntry2 = new RecurringIncome(
                "PoRk", date2, 5.0, IncomeCategory.OTHERS, Interval.MONTH, endDate1);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        RecurringExpense query = new RecurringExpense(
                "pork", date3, 5.0, ExpenseCategory.OTHERS, Interval.MONTH, endDate2);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(2, filteredList.size());
            assertTrue(filteredList.contains(targetEntry1));
            assertTrue(filteredList.contains(targetEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryFullName_noMatchingResult() {
        RecurringExpense targetEntry1 = new RecurringExpense(
                "jelly", date1, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate1);
        RecurringExpense targetEntry2 = new RecurringExpense(
                "worm", date2, 5.0, ExpenseCategory.FOOD, Interval.YEAR, endDate2);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        RecurringExpense query = new RecurringExpense(
                "yoyo", date2, 5.0, ExpenseCategory.FOOD, Interval.MONTH, endDate1);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(0, filteredList.size());
            assertFalse(filteredList.contains(targetEntry1));
            assertFalse(filteredList.contains(targetEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryNameWithAbbreviation_multipleMatchingResult() {
        RecurringExpense targetEntry1 = new RecurringExpense(
                "Pear", date1, 1.0, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        RecurringExpense dummyEntry = new RecurringExpense(
                "apple", date2, 200.2, ExpenseCategory.FOOD, Interval.MONTH, endDate2);
        RecurringIncome targetEntry2 = new RecurringIncome(
                "Green pea", date3, 5.0, IncomeCategory.OTHERS, Interval.MONTH, endDate1);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);
        entryList.add(dummyEntry);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        RecurringExpense query = new RecurringExpense(
                "peA", date2, 10.0, ExpenseCategory.OTHERS, Interval.MONTH, endDate2);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(2, filteredList.size());
            assertTrue(filteredList.contains(targetEntry1));
            assertTrue(filteredList.contains(targetEntry2));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryAmount_multipleMatchingResult() {
        RecurringExpense targetEntry1 = new RecurringExpense(
                "Ice", date1, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        RecurringIncome targetEntry2 = new RecurringIncome(
                "yoyo", date2, 3.9, IncomeCategory.OTHERS, Interval.MONTH, endDate2);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        RecurringIncome query = new RecurringIncome(
                "eel", date3, 3.9, IncomeCategory.OTHERS, Interval.MONTH, endDate1);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(2, filteredList.size());
            assertTrue(filteredList.contains(targetEntry1));
            assertTrue(filteredList.contains(targetEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryDate_oneMatchingResult() {
        RecurringIncome targetEntry = new RecurringIncome(
                "Salary", date1, 1000.0, IncomeCategory.SALARY, Interval.MONTH, endDate1);
        RecurringExpense dummyEntry = new RecurringExpense(
                "banana", date2, 5.0, ExpenseCategory.FOOD, Interval.YEAR, endDate2);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("d/");
        RecurringExpense query = new RecurringExpense(
                "yoyo", date1, 1.0, ExpenseCategory.OTHERS, Interval.MONTH, endDate2);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryCategory_oneMatchingResult() {
        RecurringExpense targetEntry = new RecurringExpense(
                "choco", date1, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        RecurringExpense dummyEntry = new RecurringExpense(
                "movie", date2, 5.0, ExpenseCategory.ENTERTAINMENT, Interval.MONTH, endDate2);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("c/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        RecurringExpense query = new RecurringExpense(
                "yoyo", date3, 1.0, ExpenseCategory.FOOD, Interval.MONTH, endDate1);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryInterval_oneMatchingResult() {
        RecurringIncome targetEntry = new RecurringIncome(
                "Salary", date1, 1000.0, IncomeCategory.SALARY, Interval.YEAR, endDate1);
        RecurringExpense dummyEntry = new RecurringExpense(
                "banana", date2, 5.0, ExpenseCategory.FOOD, Interval.MONTH, endDate2);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("i/");
        RecurringExpense query = new RecurringExpense(
                "yoyo", date1, 1.0, ExpenseCategory.OTHERS, Interval.YEAR, endDate2);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryEndDate_oneMatchingResult() {
        RecurringIncome targetEntry = new RecurringIncome(
                "Salary", date1, 1000.0, IncomeCategory.SALARY, Interval.MONTH, endDate1);
        RecurringExpense dummyEntry = new RecurringExpense(
                "banana", date2, 5.0, ExpenseCategory.FOOD, Interval.YEAR, endDate2);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("e/");
        RecurringExpense query = new RecurringExpense(
                "yoyo", date1, 1.0, ExpenseCategory.OTHERS, Interval.MONTH, endDate1);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryNameAndDate_oneMatchingResult() {
        RecurringExpense targetEntry = new RecurringExpense(
                "bread", date1, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        RecurringExpense dummyEntry1 = new RecurringExpense(
                "kinder", date2, 5.0, ExpenseCategory.FOOD, Interval.MONTH, endDate2);
        RecurringIncome dummyEntry2 = new RecurringIncome(
                "Bread", date2, 5.0, IncomeCategory.OTHERS, Interval.MONTH, endDate2);
        entryList.add(targetEntry);
        entryList.add(dummyEntry1);
        entryList.add(dummyEntry2);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("d/");
        tags.add("n/");
        RecurringExpense query = new RecurringExpense(
                "Bread", date1, 1.0, ExpenseCategory.OTHERS, Interval.MONTH, endDate2);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry1));
            assertFalse(filteredList.contains(dummyEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry1);
        entryList.remove(dummyEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryDateAndAmountAndCategory_oneMatchingResult() {
        RecurringExpense targetEntry = new RecurringExpense(
                "bread", date1, 1.0, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        RecurringExpense dummyEntry1 = new RecurringExpense(
                "kinder", date2, 5.0, ExpenseCategory.FOOD, Interval.MONTH, endDate2);
        RecurringIncome dummyEntry2 = new RecurringIncome(
                "Bread", date1, 1.0, IncomeCategory.OTHERS, Interval.MONTH, endDate1);
        entryList.add(targetEntry);
        entryList.add(dummyEntry1);
        entryList.add(dummyEntry2);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("d/");
        tags.add("c/");
        tags.add("a/");
        RecurringExpense query = new RecurringExpense(
                "Bread", date1, 1.0, ExpenseCategory.FOOD, Interval.MONTH, endDate1);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry1));
            assertFalse(filteredList.contains(dummyEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry1);
        entryList.remove(dummyEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryIntervalAndEndDate_oneMatchingResult() {
        RecurringExpense targetEntry = new RecurringExpense(
                "bread", date1, 1.0, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        RecurringExpense dummyEntry1 = new RecurringExpense(
                "kinder", date2, 5.0, ExpenseCategory.FOOD, Interval.MONTH, endDate1);
        RecurringIncome dummyEntry2 = new RecurringIncome(
                "Bread", date1, 1.0, IncomeCategory.OTHERS, Interval.MONTH, endDate2);
        entryList.add(targetEntry);
        entryList.add(dummyEntry1);
        entryList.add(dummyEntry2);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("e/");
        tags.add("i/");
        RecurringExpense query = new RecurringExpense(
                "Bread", date1, 1.0, ExpenseCategory.FOOD, Interval.YEAR, endDate1);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry1));
            assertFalse(filteredList.contains(dummyEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry1);
        entryList.remove(dummyEntry2);
    }

    @Test
    public void createLocalDateWithYearMonth_validMiddleDateWithTwoDigitDay_success() {
        YearMonth yearMonth = YearMonth.parse("2021-03");
        LocalDate expected = LocalDate.parse("2021-03-15");
        LocalDate result = financeManager.createLocalDateWithYearMonth(yearMonth, 15);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithYearMonth_singleDigitDay_success() {
        YearMonth yearMonth = YearMonth.parse("2021-03");
        LocalDate expected = LocalDate.parse("2021-03-01");
        LocalDate result = financeManager.createLocalDateWithYearMonth(yearMonth, 1);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithYearMonth_leapYearFeb29_success() {
        YearMonth yearMonth = YearMonth.parse("2020-02");
        LocalDate expected = LocalDate.parse("2020-02-29");
        LocalDate result = financeManager.createLocalDateWithYearMonth(yearMonth, 29);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithYearMonth_nonLeapYearFeb29_roundToFeb28() {
        YearMonth yearMonth = YearMonth.parse("2021-02");
        LocalDate expected = LocalDate.parse("2021-02-28");
        LocalDate result = financeManager.createLocalDateWithYearMonth(yearMonth, 29);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithYearMonth_leapYearFeb31_roundToFeb29() {
        YearMonth yearMonth = YearMonth.parse("2020-02");
        LocalDate expected = LocalDate.parse("2020-02-29");
        LocalDate result = financeManager.createLocalDateWithYearMonth(yearMonth, 31);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithYearMonth_exceedLastDayOfMonth_roundToLastDay() {
        YearMonth yearMonth = YearMonth.parse("2021-06");
        LocalDate expected = LocalDate.parse("2021-06-30");
        LocalDate result = financeManager.createLocalDateWithYearMonth(yearMonth, 31);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithIndividualValues_validTwoDigitMonthAndDay_success() {
        LocalDate expected = LocalDate.parse("2021-12-15");
        LocalDate result = financeManager.createLocalDateWithIndividualValues(2021, 12, 15);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithIndividualValues_validSingleDigitMonth_success() {
        LocalDate expected = LocalDate.parse("2021-01-15");
        LocalDate result = financeManager.createLocalDateWithIndividualValues(2021, 1, 15);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithIndividualValues_validSingleDigitDay_success() {
        LocalDate expected = LocalDate.parse("2021-12-01");
        LocalDate result = financeManager.createLocalDateWithIndividualValues(2021, 12, 1);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithIndividualValues_leapYearFeb29_success() {
        LocalDate expected = LocalDate.parse("2020-02-29");
        LocalDate result = financeManager.createLocalDateWithIndividualValues(2020, 2, 29);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithIndividualValues_nonLeapYearFeb29_roundToFeb28() {
        LocalDate expected = LocalDate.parse("2021-02-28");
        LocalDate result = financeManager.createLocalDateWithIndividualValues(2021, 2, 29);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithIndividualValues_leapYearFeb31_roundToFeb29() {
        LocalDate expected = LocalDate.parse("2020-02-29");
        LocalDate result = financeManager.createLocalDateWithIndividualValues(2020, 2, 31);
        assertEquals(result, expected);
    }

    @Test
    public void createLocalDateWithIndividualValues_exceedLastDayOfMonth_roundToLastDay() {
        LocalDate expected = LocalDate.parse("2021-06-30");
        LocalDate result = financeManager.createLocalDateWithIndividualValues(2021, 6, 31);
        assertEquals(result, expected);
    }

    LocalDate date4 = LocalDate.parse("2021-09-15", dateFormatter);

    @Test
    public void appendMonthlyEntryByMonth_middleMonth_added() {
        LocalDate endDate = LocalDate.parse("2022-09-14", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2022, 5);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.of(2022, 5, 15);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByMonth_lastMonthWithEndDateAfterRecurringDay_added() {
        LocalDate endDate = LocalDate.parse("2022-09-16", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2022, 9);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.of(2022, 9, 15);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByMonth_lastMonthWithEndDateBeforeRecurringDay_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-09-14", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2022, 9);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByMonth_lastMonthWithEndDateOnRecurringDay_added() {
        LocalDate endDate = LocalDate.parse("2022-09-15", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2022, 9);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.of(2022, 9, 15);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByMonth_lastMonthWithPeriodShorterThanOneMonth_added() {
        LocalDate endDate = LocalDate.parse("2021-10-14", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2021, 10);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByMonth_firstMonthWithPeriodShorterThanOneMonth_added() {
        LocalDate endDate = LocalDate.parse("2021-10-14", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2021, 9);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.of(2021, 9, 15);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByMonth_beforeStartDate_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-09-14", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2020, 9);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByMonth_afterEndDate_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-09-14", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2023, 9);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }


    @Test
    public void appendMonthlyEntryByMonth_recurAtLastDayOfMonthAndThatDayDoesNotExistForQueryMonth_addedRoundedDate() {
        LocalDate date = LocalDate.parse("2021-10-31", dateFormatter);
        LocalDate endDate = LocalDate.parse("2022-09-15", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth queryYM = YearMonth.of(2022, 2);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.of(2022, 2, 28);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);

        financeManager.appendMonthlyEntryByMonth(resultList, rawRecurringList, entry, queryYM);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByMonth_sameMonthAsStartMonthAndWithinRecurringPeriod_added() {
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        YearMonth queryYM = YearMonth.of(2022, 9);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.of(2022, 9, 15);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate);

        financeManager.appendYearlyEntryByMonth(resultList, rawRecurringList, entry, startYM, queryYM);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByMonth_recurAtLastDayOfMonthAndThatDayDoesNotExistForQueryYear_addedRoundedDate() {
        LocalDate date = LocalDate.parse("2020-02-29", dateFormatter);
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        YearMonth queryYM = YearMonth.of(2022, 2);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.of(2022, 2, 28);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate);

        financeManager.appendYearlyEntryByMonth(resultList, rawRecurringList, entry, startYM, queryYM);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByMonth_differentMonthFromStartMonthAndWithinRecurringPeriod_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        YearMonth queryYM = YearMonth.of(2022, 8);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendYearlyEntryByMonth(resultList, rawRecurringList, entry, startYM, queryYM);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByMonth_beforeStartDate_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        YearMonth queryYM = YearMonth.of(2020, 1);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendYearlyEntryByMonth(resultList, rawRecurringList, entry, startYM, queryYM);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByMonth_afterEndDate_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.YEAR, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        YearMonth queryYM = YearMonth.of(2023, 1);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendYearlyEntryByMonth(resultList, rawRecurringList, entry, startYM, queryYM);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByYear_yearNotWithinPeriod_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        ArrayList<Entry> resultList = new ArrayList<>();

        financeManager.appendMonthlyEntryByYear(resultList, entry, 2020);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByYear_yearWithinPeriodAndStartDateAtTheMiddleOfTheYear_added() {
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringIncome entry = new RecurringIncome(
                "bonus", date4, 3.9, IncomeCategory.SALARY, Interval.MONTH, endDate);
        entryList.add(entry);
        ArrayList<Entry> resultList = new ArrayList<>();
        LocalDate expectedDate1 = LocalDate.parse("2021-10-15", dateFormatter);
        LocalDate expectedDate2 = LocalDate.parse("2021-11-15", dateFormatter);
        LocalDate expectedDate3 = LocalDate.parse("2021-12-15", dateFormatter);
        RecurringIncome expectedEntry1 = new RecurringIncome(
                "bonus", expectedDate1, 3.9, IncomeCategory.SALARY, Interval.MONTH, endDate);
        RecurringIncome expectedEntry2 = new RecurringIncome(
                "bonus", expectedDate2, 3.9, IncomeCategory.SALARY, Interval.MONTH, endDate);
        RecurringIncome expectedEntry3 = new RecurringIncome(
                "bonus", expectedDate3, 3.9, IncomeCategory.SALARY, Interval.MONTH, endDate);

        financeManager.appendMonthlyEntryByYear(resultList, entry, 2021);
        assertEquals(4, resultList.size());
        assertTrue(resultList.contains(entry));
        assertTrue(resultList.contains(expectedEntry1));
        assertTrue(resultList.contains(expectedEntry2));
        assertTrue(resultList.contains(expectedEntry3));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryByYear_yearWithinPeriodAndRecurAtLastDayOfMonth_addedRoundedDate() {
        LocalDate date = LocalDate.parse("2022-01-31", dateFormatter);
        LocalDate endDate = LocalDate.parse("2022-05-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        ArrayList<Entry> resultList = new ArrayList<>();
        LocalDate expectedDate1 = LocalDate.parse("2022-02-28", dateFormatter);
        LocalDate expectedDate2 = LocalDate.parse("2022-03-31", dateFormatter);
        LocalDate expectedDate3 = LocalDate.parse("2022-04-30", dateFormatter);
        RecurringExpense expectedEntry1 = new RecurringExpense(
                "jelly", expectedDate1, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        RecurringExpense expectedEntry2 = new RecurringExpense(
                "jelly", expectedDate2, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        RecurringExpense expectedEntry3 = new RecurringExpense(
                "jelly", expectedDate3, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);

        financeManager.appendMonthlyEntryByYear(resultList, entry, 2022);
        assertEquals(4, resultList.size());
        assertTrue(resultList.contains(entry));
        assertTrue(resultList.contains(expectedEntry1));
        assertTrue(resultList.contains(expectedEntry2));
        assertTrue(resultList.contains(expectedEntry3));
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByYear_yearWithinPeriodAndMonthNotWithinPeriod_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-05-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        ArrayList<Entry> resultList = new ArrayList<>();

        financeManager.appendYearlyEntryByYear(resultList, entry, startYM, 2022);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByYear_yearNotWithinPeriod_notAdded() {
        LocalDate endDate = LocalDate.parse("2022-05-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        ArrayList<Entry> resultList = new ArrayList<>();

        financeManager.appendYearlyEntryByYear(resultList, entry, startYM, 2020);
        assertEquals(0, resultList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryByYear_yearMonthWithinPeriod_added() {
        LocalDate endDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringExpense entry = new RecurringExpense(
                "jelly", date4, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);
        entryList.add(entry);
        YearMonth startYM = YearMonth.from(entry.getDate());
        ArrayList<Entry> resultList = new ArrayList<>();
        LocalDate expectedDate = LocalDate.parse("2022-09-15", dateFormatter);
        RecurringExpense expectedEntry = new RecurringExpense(
                "jelly", expectedDate, 3.9, ExpenseCategory.FOOD, Interval.MONTH, endDate);

        financeManager.appendYearlyEntryByYear(resultList, entry, startYM, 2022);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(expectedEntry));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryBetweenTwoDates_periodWithinTwoDates_added() {
        LocalDate endRecurringDate = LocalDate.parse("2022-10-02", dateFormatter);
        RecurringIncome entry = new RecurringIncome(
                "bonus", date4, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        entryList.add(entry);

        LocalDate startDate = LocalDate.parse("2021-06-15", dateFormatter);
        LocalDate endDate = LocalDate.parse("2021-12-12", dateFormatter);
        LocalDate expectedDate1 = LocalDate.parse("2021-10-15", dateFormatter);
        LocalDate expectedDate2 = LocalDate.parse("2021-11-15", dateFormatter);
        RecurringIncome expectedEntry1 = new RecurringIncome(
                "bonus", expectedDate1, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        RecurringIncome expectedEntry2 = new RecurringIncome(
                "bonus", expectedDate2, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendMonthlyEntryBetweenTwoDates(resultList, rawRecurringList, entry, startDate, endDate);
        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(entry));
        assertTrue(resultList.contains(expectedEntry1));
        assertTrue(resultList.contains(expectedEntry2));
        assertEquals(1, rawRecurringList.size());
        assertTrue(rawRecurringList.contains(entry));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryBetweenTwoDates_periodPartiallyWithinTwoDates_added() {
        LocalDate endRecurringDate = LocalDate.parse("2022-02-13", dateFormatter);
        RecurringIncome entry = new RecurringIncome(
                "bonus", date4, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        entryList.add(entry);

        LocalDate startDate = LocalDate.parse("2021-06-15", dateFormatter);
        LocalDate endDate = LocalDate.parse("2021-12-10", dateFormatter);
        LocalDate expectedDate1 = LocalDate.parse("2021-10-15", dateFormatter);
        LocalDate expectedDate2 = LocalDate.parse("2021-11-15", dateFormatter);
        RecurringIncome expectedEntry1 = new RecurringIncome(
                "bonus", expectedDate1, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        RecurringIncome expectedEntry2 = new RecurringIncome(
                "bonus", expectedDate2, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendMonthlyEntryBetweenTwoDates(resultList, rawRecurringList, entry, startDate, endDate);
        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(entry));
        assertTrue(resultList.contains(expectedEntry1));
        assertTrue(resultList.contains(expectedEntry2));
        assertEquals(1, rawRecurringList.size());
        assertTrue(rawRecurringList.contains(entry));
        entryList.remove(entry);
    }

    @Test
    public void appendMonthlyEntryBetweenTwoDates_periodNotWithinTwoDates_notAdded() {
        LocalDate endRecurringDate = LocalDate.parse("2022-02-13", dateFormatter);
        RecurringIncome entry = new RecurringIncome(
                "bonus", date4, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        entryList.add(entry);

        LocalDate startDate = LocalDate.parse("2023-06-15", dateFormatter);
        LocalDate endDate = LocalDate.parse("2024-12-10", dateFormatter);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendMonthlyEntryBetweenTwoDates(resultList, rawRecurringList, entry, startDate, endDate);
        assertEquals(0, resultList.size());
        assertEquals(0, rawRecurringList.size());
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryBetweenTwoDates_periodWithinTwoDates_added() {
        LocalDate endRecurringDate = LocalDate.parse("2022-12-23", dateFormatter);
        RecurringIncome entry = new RecurringIncome(
                "bonus", date4, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        entryList.add(entry);

        LocalDate startDate = LocalDate.parse("2021-06-15", dateFormatter);
        LocalDate endDate = LocalDate.parse("2023-12-12", dateFormatter);
        LocalDate expectedDate1 = LocalDate.parse("2022-09-15", dateFormatter);
        RecurringIncome expectedEntry1 = new RecurringIncome(
                "bonus", expectedDate1, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendYearlyEntryBetweenTwoDates(resultList, rawRecurringList, entry, startDate, endDate);
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(entry));
        assertTrue(resultList.contains(expectedEntry1));
        assertEquals(1, rawRecurringList.size());
        assertTrue(rawRecurringList.contains(entry));
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryBetweenTwoDates_periodPartiallyWithinTwoDates_added() {
        LocalDate endRecurringDate = LocalDate.parse("2022-12-23", dateFormatter);
        RecurringIncome entry = new RecurringIncome(
                "bonus", date4, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        entryList.add(entry);

        LocalDate startDate = LocalDate.parse("2021-06-15", dateFormatter);
        LocalDate endDate = LocalDate.parse("2022-05-12", dateFormatter);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendYearlyEntryBetweenTwoDates(resultList, rawRecurringList, entry, startDate, endDate);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(entry));
        assertEquals(1, rawRecurringList.size());
        assertTrue(rawRecurringList.contains(entry));
        entryList.remove(entry);
    }

    @Test
    public void appendYearlyEntryBetweenTwoDates_periodNotWithinTwoDates_notAdded() {
        LocalDate endRecurringDate = LocalDate.parse("2022-12-23", dateFormatter);
        RecurringIncome entry = new RecurringIncome(
                "bonus", date4, 3.9, IncomeCategory.SALARY, Interval.MONTH, endRecurringDate);
        entryList.add(entry);

        LocalDate startDate = LocalDate.parse("2016-06-15", dateFormatter);
        LocalDate endDate = LocalDate.parse("2018-05-12", dateFormatter);
        ArrayList<Entry> resultList = new ArrayList<>();
        ArrayList<Entry> rawRecurringList = new ArrayList<>();

        financeManager.appendYearlyEntryBetweenTwoDates(resultList, rawRecurringList, entry, startDate, endDate);
        assertEquals(0, resultList.size());
        assertEquals(0, rawRecurringList.size());
        entryList.remove(entry);
    }
}