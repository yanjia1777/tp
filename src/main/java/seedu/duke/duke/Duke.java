package seedu.duke.duke;

import seedu.duke.budget.Budget;
import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.Command;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.parser.Parser;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.MintLogger;
import seedu.duke.utility.Ui;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duke {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Ui ui;
    private Parser parser;
    private NormalFinanceManager normalFinanceManager;
    private RecurringFinanceManager recurringFinanceManager;
    private BudgetManager budgetManager;
    private DataManagerActions dataManagerActions;
    private BudgetDataManager budgetDataManager;
    private NormalListDataManager normalListDataManager;
    private RecurringListDataManager recurringListDataManager;

    public Duke() {
        this.ui = new Ui();
        this.parser = new Parser();
        this.normalFinanceManager = new NormalFinanceManager();
        this.recurringFinanceManager = new RecurringFinanceManager();
        this.budgetManager = new BudgetManager();
        this.dataManagerActions = new DataManagerActions();
        this.budgetDataManager = new BudgetDataManager();
        this.normalListDataManager = new NormalListDataManager();
        this.recurringListDataManager = new RecurringListDataManager();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Duke().run();
    }

    public void run() {
        ui.printGreetings();
        Scanner in = new Scanner(System.in);
        MintLogger.run();
        logger.log(Level.INFO, "User started Mint");
        //call financeManager instead
        normalListDataManager.loadPreviousFileContents(normalFinanceManager.entryList);
        recurringListDataManager.loadPreviousFileContents(recurringFinanceManager.recurringEntryList);
        budgetDataManager.loadFromTextFile(budgetManager.getBudgetList());
        while (true) {
            String userInput = ui.readUserInput();
            Command command = parser.parseCommand(userInput);
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            if (command.isExit()) {
                break;
            }
        }
        logger.log(Level.INFO, "User exited Mint");
    }
}
