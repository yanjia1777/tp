package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.DeleteAllCommand;
import seedu.duke.commands.ViewCommand;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Interval;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.parser.ViewOptions;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

public class DeleteAllFunctionTest {
    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
    RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    BudgetManager budgetManager = new BudgetManager();
    NormalListDataManager normalListDataManager = new NormalListDataManager();
    DataManagerActions dataManagerActions = new DataManagerActions();
    RecurringListDataManager recurringListDataManager = new RecurringListDataManager();
    BudgetDataManager budgetDataManager = new BudgetDataManager();
    Ui ui = new Ui();

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
            ArrayList<Entry> expectedArray = new ArrayList<>();
            assertEquals(expectedArray, normalFinanceManager.entryList);
            assertEquals(expectedArray, recurringFinanceManager.recurringEntryList);
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
