package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.Logic.commands.DeleteAllCommand;
import seedu.duke.Model.entries.Entry;
import seedu.duke.Model.entries.Expense;
import seedu.duke.Model.entries.ExpenseCategory;
import seedu.duke.Model.entries.Income;
import seedu.duke.Model.entries.IncomeCategory;
import seedu.duke.Model.entries.Interval;
import seedu.duke.Model.entries.RecurringExpense;
import seedu.duke.Model.entries.RecurringIncome;
import seedu.duke.Utility.MintException;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.Logic.parser.ValidityChecker.dateFormatter;

public class DeleteAllFunctionTest {
    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
    RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    NormalListDataManager normalListDataManager = new NormalListDataManager();
    RecurringListDataManager recurringListDataManager = new RecurringListDataManager();

    @Test
    void delete_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            DeleteAllCommand command = new DeleteAllCommand(true, true);
            command.deleteAllNormal(normalFinanceManager, normalListDataManager);
            command.deleteAllRecurring(recurringFinanceManager, recurringListDataManager);
            ArrayList<Entry> expectedArray = new ArrayList<>();
            assertEquals(expectedArray, normalFinanceManager.entryList);
            assertEquals(expectedArray, recurringFinanceManager.recurringEntryList);
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteNormal_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            DeleteAllCommand command = new DeleteAllCommand(true, false);
            command.deleteAllNormal(normalFinanceManager, normalListDataManager);
            ArrayList<Entry> expectedNormalArray = new ArrayList<>();
            ArrayList<Entry> expectedRecurringArray = new ArrayList<>();
            expectedRecurringArray.add(expenseR);
            expectedRecurringArray.add(incomeR);
            assertEquals(expectedNormalArray, normalFinanceManager.entryList);
            assertEquals(expectedRecurringArray, recurringFinanceManager.recurringEntryList);
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteRecurring_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            DeleteAllCommand command = new DeleteAllCommand(false, true);
            command.deleteAllRecurring(recurringFinanceManager, recurringListDataManager);
            ArrayList<Entry> expectedNormalArray = new ArrayList<>();
            expectedNormalArray.add(expense);
            expectedNormalArray.add(income);
            ArrayList<Entry> expectedRecurringArray = new ArrayList<>();
            assertEquals(expectedNormalArray, normalFinanceManager.entryList);
            assertEquals(expectedRecurringArray, recurringFinanceManager.recurringEntryList);
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    public Expense expense() {
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory categoryE = ExpenseCategory.FOOD;
        return new Expense(name, date, amount, categoryE);
    }

    public RecurringExpense recurringExpense() {
        String name = "Maid Cafe";
        LocalDate date = LocalDate.parse("2012-06-06", dateFormatter);
        Interval interval = Interval.YEAR;
        LocalDate endDate = LocalDate.parse("2021-11-06", dateFormatter);
        double amount = Double.parseDouble("14.6");
        ExpenseCategory categoryE = ExpenseCategory.ENTERTAINMENT;
        return new RecurringExpense(name, date, amount, categoryE, interval, endDate);
    }

    public Income income() {
        String name = "Lottery";
        LocalDate date = LocalDate.parse("2015-12-15", dateFormatter);
        double amount = Double.parseDouble("250000");
        IncomeCategory categoryI = IncomeCategory.GIFT;
        return new Income(name, date, amount, categoryI);
    }

    public RecurringIncome recurringIncome() {
        String name = "OnlyFans";
        LocalDate date = LocalDate.parse("2021-06-09", dateFormatter);
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-11-06", dateFormatter);
        double amount = Double.parseDouble("300");
        IncomeCategory categoryI = IncomeCategory.COMMISSION;
        return new RecurringIncome(name, date, amount, categoryI, interval, endDate);
    }

}
