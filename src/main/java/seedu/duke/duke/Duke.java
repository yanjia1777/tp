package seedu.duke.duke;

import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.Command;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.parser.Parser;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.utility.MintLogger;
import seedu.duke.utility.Ui;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duke {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String CATEGORY_FILE_PATH = "data" + File.separator + "MintCategory.txt";

    private Ui ui;
    private Parser parser;
    private NormalFinanceManager normalFinanceManager;
    private RecurringFinanceManager recurringFinanceManager;
    private BudgetManager budgetManager;

    public Duke() {
        this.ui = new Ui();
        this.parser = new Parser();
        this.normalFinanceManager = new NormalFinanceManager();
        this.recurringFinanceManager = new RecurringFinanceManager();
        this.budgetManager = new BudgetManager();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Duke().run();
    }

    public void run() {
        ui.printGreetings();
        DataManagerActions dataManagerActions = new DataManagerActions(FILE_PATH);
        MintLogger.run();
        logger.log(Level.INFO, "User started Mint");
        //call financeManager instead
        //dataManagerActions.printPreviousFileContents(entryList);

        while (true) {
            String userInput = ui.readUserInput();
            if (ui.hasUnsafeCharacters(userInput)) {
                ui.printUnsafeCharacters();
                continue;
            }
            Command command = parser.parseCommand(userInput);
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, ui);
            if (command.isExit()) {
                break;
            }
            //store
        }
        logger.log(Level.INFO, "User exited Mint");
    }
}
