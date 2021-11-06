package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.ViewCategoriesCommand;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewCategoriesCommandTest {
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

        ViewCategoriesCommand command = new ViewCategoriesCommand();
        command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                dataManagerActions, recurringListDataManager, budgetDataManager, ui);

        String expectedOutput = "Here are the categories and its tag number\n"
                + "Expenses           | Income\n"
                + "c/0 FOOD           | c/0 ALLOWANCE\n"
                + "c/1 ENTERTAINMENT  | c/1 WAGES\n"
                + "c/2 TRANSPORTATION | c/2 SALARY\n"
                + "c/3 HOUSEHOLD      | c/3 INTERESTED\n"
                + "c/4 APPAREL        | c/4 INVESTMENT\n"
                + "c/5 BEAUTY         | c/5 COMMISSION\n"
                + "c/6 GIFT           | c/6 GIFT\n"
                + "c/7 OTHERS         | c/7 OTHERS\n"
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
