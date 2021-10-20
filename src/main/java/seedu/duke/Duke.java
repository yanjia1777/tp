package seedu.duke;

import seedu.duke.storage.DataManagerActions;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duke {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String CATEGORY_FILE_PATH = "data" + File.separator + "MintCategory.txt";

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Duke().run();
    }

    public void run() {
        Ui.startup();
        Scanner in = new Scanner(System.in);
        CategoryList.initialiseCategories();
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();
        DataManagerActions dataManagerActions = new DataManagerActions(FILE_PATH);
        MintLogger.run();
        logger.log(Level.INFO, "User started Mint");
        dataManagerActions.printPreviousFileContents(expenseList);
        while (true) {
            try {
                String userInput = in.nextLine();
                if (parser.executeCommand(userInput, expenseList) == -1) {
                    break;
                }
            } catch (MintException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("No new line entered");
            }
        }
        logger.log(Level.INFO, "User exited Mint");
    }
}
