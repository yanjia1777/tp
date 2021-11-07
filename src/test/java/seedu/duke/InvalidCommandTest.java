package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.model.budget.BudgetManager;
import seedu.duke.logic.commands.Command;
import seedu.duke.model.financemanager.NormalFinanceManager;
import seedu.duke.model.financemanager.RecurringFinanceManager;
import seedu.duke.logic.parser.Parser;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidCommandTest {
    @Test
    void helpCommandTest_validInput_success() {
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

        Parser parser = new Parser();
        Command command = parser.parseCommand("hello");
        command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                dataManagerActions, recurringListDataManager, budgetDataManager, ui);

        String expectedOutput = "Sorry I don't know what that means. :(" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
