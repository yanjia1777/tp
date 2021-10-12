package seedu.duke;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        Ui.startup();
        Scanner in = new Scanner(System.in);
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();
        MintLogger.run();
        logger.log(Level.INFO, "User started Mint");
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
