package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.Logic.commands.AddCommand;
import seedu.duke.Logic.commands.AddRecurringCommand;
import seedu.duke.Logic.commands.Command;
import seedu.duke.Logic.commands.DeleteAllCommand;
import seedu.duke.Logic.commands.DeleteCommand;
import seedu.duke.Logic.commands.DeleteRecurringCommand;
import seedu.duke.Logic.commands.EditCommand;
import seedu.duke.Logic.commands.EditRecurringCommand;
import seedu.duke.Logic.commands.ExitCommand;
import seedu.duke.Logic.commands.HelpCommand;
import seedu.duke.Logic.commands.InvalidCommand;
import seedu.duke.Logic.commands.SetBudgetCommand;
import seedu.duke.Logic.commands.ViewBudgetCommand;
import seedu.duke.Logic.commands.ViewCategoriesCommand;
import seedu.duke.Logic.commands.ViewCommand;
import seedu.duke.Model.entries.Expense;
import seedu.duke.Model.entries.Income;
import seedu.duke.Logic.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @Test
    void addCommandThruParser_validInput_returnaddCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("add n/test a/1");
        assertTrue(command instanceof AddCommand);
    }

    @Test
    void deleteCommandThruParser_validInput_returnDeleteCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("delete n/test");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    void editCommandThruParser_validInput_returnEditCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("edit n/test");
        assertTrue(command instanceof EditCommand);
    }

    @Test
    void addRecurringCommandThruParser_validInput_returnAddRecurringCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("addR n/test a/1 i/month");
        assertTrue(command instanceof AddRecurringCommand);
    }

    @Test
    void deleteRecurringCommandThruParser_validInput_returnDeleteRecurringCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deleteR n/test a/1 i/month");
        assertTrue(command instanceof DeleteRecurringCommand);
    }

    @Test
    void editRecurringCommandThruParser_validInput_returnEditRecurringCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("editR n/test a/1 i/month");
        assertTrue(command instanceof EditRecurringCommand);
    }

    @Test
    void setBudgetCommandThruParser_validInput_returnSetBudgetCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("set c/0 a/100");
        assertTrue(command instanceof SetBudgetCommand);
    }

    @Test
    void viewBudgetCommandThruParser_validInput_returnViewBudgetCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("budget");
        assertTrue(command instanceof ViewBudgetCommand);
    }

    @Test
    void deleteAllCommandThruParser_validInput_returnDeleteAllCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deleteAll");
        assertTrue(command instanceof DeleteAllCommand);
    }

    @Test
    void viewCommandThruParser_validInput_returnViewCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("view");
        assertTrue(command instanceof ViewCommand);
    }

    @Test
    void viewCategoriesThruParser_validInput_returnViewCategoriesCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("cat");
        assertTrue(command instanceof ViewCategoriesCommand);
    }

    @Test
    void helpThruParser_validInput_returnHelpCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("help");
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    void exitThruParser_validInput_returnExitCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("exit");
        assertTrue(command instanceof ExitCommand);
    }


    @Test
    void invalidCommandThruParser_invalidCommand_returnInvalidCommandObject() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("hello");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    void addExpenseThruParser_validInput_checkTypeIsExpense() {
        Parser parser = new Parser();
        parser.parseCommand("add n/test a/0");
        assertTrue(parser.checkType() instanceof Expense);
    }

    @Test
    void addIncomeThruParser_validInput_checkTypeIsIncome() {
        Parser parser = new Parser();
        parser.parseCommand("add income n/test a/0");
        assertTrue(parser.checkType() instanceof Income);
    }

}





