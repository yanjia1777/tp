package seedu.duke;

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
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();
        while (true) {
            String userInput = in.nextLine();
            try {
                parser.parseInput(userInput);
                if (parser.executeCommand(userInput, expenseList) == -1) {
                    break;
                }
            } catch (MintException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
