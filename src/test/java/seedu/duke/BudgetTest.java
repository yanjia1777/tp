package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.Model.budget.Budget;
import seedu.duke.Model.budget.BudgetManager;
import seedu.duke.Model.entries.Expense;
import seedu.duke.Model.entries.ExpenseCategory;
import seedu.duke.Model.entries.Interval;
import seedu.duke.Model.entries.RecurringExpense;
import seedu.duke.Utility.MintException;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BudgetTest {
    @Test
    void addNormalAndRecurringExpenses_sameCategory_bothShownInMonthlyBudget() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        BudgetManager budgetManager = new BudgetManager();
        Ui ui = new Ui();

        String expenseName = "cheese";
        LocalDate expenseDate = LocalDate.now(); //current month, so it shows when we view budget
        double expenseAmt = 25.0;
        ExpenseCategory expenseCat = ExpenseCategory.FOOD;
        Expense expense = new Expense(expenseName, expenseDate, expenseAmt, expenseCat);

        String recurName = "redmart delivery";
        LocalDate recurDate = LocalDate.now(); //current month, so it shows when we view budget
        LocalDate recurEndDate = LocalDate.parse("2200-12-31");
        double recurAmt = 50.0;
        ExpenseCategory recurCat = ExpenseCategory.FOOD;
        Interval recurInterval = Interval.MONTH;

        RecurringExpense recurExpense = new RecurringExpense(recurName, recurDate, recurAmt, recurCat,
                recurInterval, recurEndDate);
        try {
            normalFinanceManager.addEntry(expense);
            recurringFinanceManager.addEntry(recurExpense);
            ui.printBudgetBreakdown(budgetManager.getBudgetList(), normalFinanceManager.getEntryList(),
                    recurringFinanceManager.getCopyOfRecurringEntryList());
        } catch (MintException e) {
            e.printStackTrace();
        }
        String expectedOutput = String.format("Here is the budget for %s %s"
                + System.lineSeparator(), LocalDate.now().getMonth(), LocalDate.now().getYear())
                + "   Category    | Amount | Budget  | Percentage" + System.lineSeparator()
                + "     FOOD      | $75.00 / Not set | " + System.lineSeparator()
                + "ENTERTAINMENT  |  $0.00 / Not set | " + System.lineSeparator()
                + "TRANSPORTATION |  $0.00 / Not set | " + System.lineSeparator()
                + "  HOUSEHOLD    |  $0.00 / Not set | " + System.lineSeparator()
                + "   APPAREL     |  $0.00 / Not set | " + System.lineSeparator()
                + "    BEAUTY     |  $0.00 / Not set | " + System.lineSeparator()
                + "     GIFT      |  $0.00 / Not set | " + System.lineSeparator()
                + "    OTHERS     |  $0.00 / Not set | " + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void addNormalAndRecurringExpenses_sameCategory_bothShownInMonthlySpending() {
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        BudgetManager budgetManager = new BudgetManager();

        String expenseName = "cheese";
        LocalDate expenseDate = LocalDate.now(); //current month, so it shows when we view budget
        double expenseAmt = 10.0;
        ExpenseCategory expenseCat = ExpenseCategory.FOOD;
        Expense expense = new Expense(expenseName, expenseDate, expenseAmt, expenseCat);

        String recurMName = "redmart delivery";
        LocalDate recurMDate = LocalDate.now(); //current month, so it shows when we view budget
        LocalDate recurMEndDate = LocalDate.parse("2200-12-31");
        double recurMAmt = 20.0;
        ExpenseCategory recurMCat = ExpenseCategory.FOOD;
        Interval recurMInterval = Interval.MONTH;
        RecurringExpense recurMExpense = new RecurringExpense(recurMName, recurMDate, recurMAmt, recurMCat,
                recurMInterval, recurMEndDate);

        String recurYName = "halloween";
        LocalDate recurYDate = LocalDate.now(); //current month, so it shows when we view budget
        LocalDate recurYEndDate = LocalDate.parse("2200-12-31");
        double recurYAmt = 30.0;
        ExpenseCategory recurYCat = ExpenseCategory.FOOD;
        Interval recurYInterval = Interval.YEAR;

        RecurringExpense recurYExpense = new RecurringExpense(recurYName, recurYDate, recurYAmt, recurYCat,
                recurYInterval, recurYEndDate);
        try {
            normalFinanceManager.addEntry(expense);
            recurringFinanceManager.addEntry(recurMExpense);
            recurringFinanceManager.addEntry(recurYExpense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        Budget foodBudget = budgetManager.getMonthlyBudgetFromCategory(ExpenseCategory.FOOD);
        double totalSpending = foodBudget.getMonthlySpending(normalFinanceManager.getEntryList(),
                recurringFinanceManager.getCopyOfRecurringEntryList());
        assertEquals("FOOD", foodBudget.getName());
        assertEquals(expenseAmt + recurMAmt + recurYAmt, totalSpending);
    }
}
