package seedu.duke.duke;

import seedu.duke.Model.budget.BudgetManager;
import seedu.duke.Logic.commands.Command;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Logic.parser.Parser;
import seedu.duke.Storage.BudgetDataManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;
import seedu.duke.Utility.MintLogger;
import seedu.duke.Ui.Ui;


import java.util.logging.Level;
import java.util.logging.Logger;

//@@author yanjia1777
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
        MintLogger.run();
        logger.log(Level.INFO, "User started Mint");
        //call financeManager instead
        normalListDataManager.loadPreviousFileContents(normalFinanceManager.entryList);
        recurringListDataManager.loadPreviousFileContents(recurringFinanceManager.recurringEntryList);
        budgetDataManager.loadFromTextFile(budgetManager.getBudgetList());
        String userInput = ui.readUserInput();
        while (userInput != null) {
            if (ui.hasUnsafeCharacters(userInput)) {
                ui.printUnsafeCharacters();
            } else {
                Command command = parser.parseCommand(userInput);
                command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                        dataManagerActions, recurringListDataManager, budgetDataManager, ui);
                if (command.isExit()) {
                    break;
                }
            }
            userInput = ui.readUserInput();
        }
        ui.shutdown();
        logger.log(Level.INFO, "User exited Mint");
    }
}
