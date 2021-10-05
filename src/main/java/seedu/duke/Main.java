package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        Ui.startup();
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser(userInput);
        while (true) {
            if (parser.executeCommand(expenseList) == -1) {
                break;
            }
            userInput = in.nextLine();
            parser.parseInput(userInput);
        }
    }
}
