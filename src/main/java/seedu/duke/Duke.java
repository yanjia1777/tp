package seedu.duke;

import seedu.duke.commands.Command;
import seedu.duke.parser.Parser;
import seedu.duke.storage.DataManagerActions;

import java.io.File;
import java.lang.reflect.Executable;
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
    private FinancialManager financialManager;

    public Duke() {
        this.ui = new Ui();
        this.parser = new Parser();
        this.financialManager = new FinancialManager();
    }

    public void run() {
        ui.printGreetings();
        DataManagerActions dataManagerActions = new DataManagerActions(FILE_PATH);
        MintLogger.run();
        logger.log(Level.INFO, "User started Mint");
        dataManagerActions.printPreviousFileContents(financialManager.entryList);
        while (true) {
            String userInput = ui.readUserInput();
            Command command = parser.parseCommand(userInput, financialManager);
            command.execute(financialManager, ui);
            if (command.isExit()) {
                break;
            }
        }
        //insert storing here
        logger.log(Level.INFO, "User exited Mint");
        ui.printGoodbye();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}
