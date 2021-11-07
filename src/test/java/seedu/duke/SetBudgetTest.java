package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.Model.budget.Budget;
import seedu.duke.Model.budget.BudgetManager;
import seedu.duke.Logic.commands.SetBudgetCommand;
import seedu.duke.Model.entries.ExpenseCategory;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Storage.BudgetDataManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;
import seedu.duke.Ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetBudgetTest {

    @Test
    void setFoodBudget_AllFieldsValid_success() {
        BudgetManager budgetManager = new BudgetManager();
        ExpenseCategory category = ExpenseCategory.FOOD;
        double amount = 1000.00;
        budgetManager.setBudget(category, amount);
        Budget foodBudget = budgetManager.getMonthlyBudgetFromCategory(category);
        assertEquals(amount, foodBudget.getLimit());
    }

    @Test
    void setMultiple_AllFieldsValid_printCorrectUi() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BudgetManager budgetManager = new BudgetManager();
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        Ui ui = new Ui();
        budgetManager.setBudget(ExpenseCategory.FOOD, 100);
        budgetManager.setBudget(ExpenseCategory.ENTERTAINMENT, 200);
        ui.printBudgetBreakdown(budgetManager.getBudgetList(), normalFinanceManager.getEntryList(),
                recurringFinanceManager.getCopyOfRecurringEntryList());
        String expectedOutput = String.format("Here is the budget for %s %s"
                + System.lineSeparator(), LocalDate.now().getMonth(), LocalDate.now().getYear())
                + "   Category    | Amount | Budget  | Percentage" + System.lineSeparator()
                + "     FOOD      |  $0.00 / $100.00 | " + System.lineSeparator()
                + "ENTERTAINMENT  |  $0.00 / $200.00 | " + System.lineSeparator()
                + "TRANSPORTATION |  $0.00 / Not set | " + System.lineSeparator()
                + "  HOUSEHOLD    |  $0.00 / Not set | " + System.lineSeparator()
                + "   APPAREL     |  $0.00 / Not set | " + System.lineSeparator()
                + "    BEAUTY     |  $0.00 / Not set | " + System.lineSeparator()
                + "     GIFT      |  $0.00 / Not set | " + System.lineSeparator()
                + "    OTHERS     |  $0.00 / Not set | " + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void setAllBudget_AllFieldsValid_success() {
        BudgetManager budgetManager = new BudgetManager();
        ExpenseCategory foodCategory = ExpenseCategory.FOOD;
        double amount = 100.00;
        budgetManager.setBudget(foodCategory, amount);
        Budget foodBudget = budgetManager.getMonthlyBudgetFromCategory(foodCategory);
        assertEquals(100, foodBudget.getLimit());

        ExpenseCategory entertainmentCategory = ExpenseCategory.ENTERTAINMENT;
        amount = 200;
        budgetManager.setBudget(entertainmentCategory, amount);
        Budget entertainmentBudget = budgetManager.getMonthlyBudgetFromCategory(entertainmentCategory);
        assertEquals(200, entertainmentBudget.getLimit());

        ExpenseCategory transportationCategory = ExpenseCategory.TRANSPORTATION;
        amount = 300;
        budgetManager.setBudget(transportationCategory, amount);
        Budget transportationBudget = budgetManager.getMonthlyBudgetFromCategory(transportationCategory);
        assertEquals(300, transportationBudget.getLimit());

        ExpenseCategory householdCategory = ExpenseCategory.HOUSEHOLD;
        amount = 400;
        budgetManager.setBudget(householdCategory, amount);
        Budget householdBudget = budgetManager.getMonthlyBudgetFromCategory(householdCategory);
        assertEquals(400, householdBudget.getLimit());

        ExpenseCategory apparelCategory = ExpenseCategory.APPAREL;
        amount = 500;
        budgetManager.setBudget(apparelCategory, amount);
        Budget apparelBudget = budgetManager.getMonthlyBudgetFromCategory(apparelCategory);
        assertEquals(500, apparelBudget.getLimit());

        ExpenseCategory beautyCategory = ExpenseCategory.BEAUTY;
        amount = 600;
        budgetManager.setBudget(beautyCategory, amount);
        Budget beautyBudget = budgetManager.getMonthlyBudgetFromCategory(beautyCategory);
        assertEquals(600, beautyBudget.getLimit());

        ExpenseCategory giftCategory = ExpenseCategory.GIFT;
        amount = 700;
        budgetManager.setBudget(giftCategory, amount);
        Budget giftBudget = budgetManager.getMonthlyBudgetFromCategory(giftCategory);
        assertEquals(700, giftBudget.getLimit());

        ExpenseCategory othersCategory = ExpenseCategory.OTHERS;
        amount = 800;
        budgetManager.setBudget(othersCategory, amount);
        Budget othersBudget = budgetManager.getMonthlyBudgetFromCategory(othersCategory);
        assertEquals(800, othersBudget.getLimit());
    }

    @Test
    void setBudgetCommandTest_validInputs_returnConfirmationMessage() {
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        BudgetManager budgetManager = new BudgetManager();
        NormalListDataManager normalListDataManager = new NormalListDataManager();
        DataManagerActions dataManagerActions = new DataManagerActions();
        RecurringListDataManager recurringListDataManager = new RecurringListDataManager();
        BudgetDataManager budgetDataManager = new BudgetDataManager();
        Ui ui = new Ui();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SetBudgetCommand setBudgetCommand = new SetBudgetCommand(ExpenseCategory.FOOD, 1000);
        setBudgetCommand.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                dataManagerActions, recurringListDataManager, budgetDataManager, ui);
        String expectedOutput = "Budget for FOOD set to $1000.00\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
