package seedu.duke;

import seedu.duke.parser.Parser;
import seedu.duke.storage.DataManagerActions;

import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duke {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String CATEGORY_FILE_PATH = "data" + File.separator + "MintCategory.txt";

    private Ui ui;
    private Parser parser;
    private FinanceManager financeManager;

    public Duke() {
        this.ui = new Ui();
        this.parser = new Parser();
        this.financeManager = new FinanceManager();
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
        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
        DataManagerActions dataManagerActions = new DataManagerActions(FILE_PATH);
        MintLogger.run();
        logger.log(Level.INFO, "User started Mint");
        //call financeManager instead
        //dataManagerActions.printPreviousFileContents(entryList);

        while (true) {
            String userInput = ui.readUserInput();
            Command command = parser.parseCommand(userInput, financeManager.entryList, recurringExpenseList);
            command.execute(financeManager, ui);
            if (command.isExit()) {
                break;
            }
            //store
        }
        logger.log(Level.INFO, "User exited Mint");
    }
}
