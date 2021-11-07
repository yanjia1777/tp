package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.model.budget.BudgetManager;
import seedu.duke.logic.commands.ViewBudgetCommand;
import seedu.duke.model.financemanager.NormalFinanceManager;
import seedu.duke.model.financemanager.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewBudgetCommandTest {
    @Test
    void viewCategoryCommandTest_validInput_success() {
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

        ViewBudgetCommand command = new ViewBudgetCommand();
        command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                dataManagerActions, recurringListDataManager, budgetDataManager, ui);

        String expectedOutput = String.format("Here is the budget for %s %s"
                + System.lineSeparator(), LocalDate.now().getMonth(), LocalDate.now().getYear())
                + "   Category    | Amount | Budget  | Percentage" + System.lineSeparator()
                + "     FOOD      |  $0.00 / Not set | " + System.lineSeparator()
                + "ENTERTAINMENT  |  $0.00 / Not set | " + System.lineSeparator()
                + "TRANSPORTATION |  $0.00 / Not set | " + System.lineSeparator()
                + "  HOUSEHOLD    |  $0.00 / Not set | " + System.lineSeparator()
                + "   APPAREL     |  $0.00 / Not set | " + System.lineSeparator()
                + "    BEAUTY     |  $0.00 / Not set | " + System.lineSeparator()
                + "     GIFT      |  $0.00 / Not set | " + System.lineSeparator()
                + "    OTHERS     |  $0.00 / Not set | " + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
