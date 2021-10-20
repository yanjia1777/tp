package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    protected static final String INDENT = "    ";
    public static final String SUCCESSFUL_EDIT_MESSAGE = "Got it! I will update the fields accordingly!";
    public static final String UNSUCCESSFUL_EDIT_MESSAGE = "No difference detected!"
            + "I was unable to perform any edits! "
            + "Please check that you have made changes or included the tags of the fields you wish to edit! :(";

    protected static final String LINE_SEPARATOR = System.lineSeparator();
    protected static final int INDEX_CANCEL = -1;
    protected static final String CANCEL_MESSAGE = " To cancel, type \"cancel\"";
    public static final String MISSING_FILE_MESSAGE = "Missing data detected! Creating the necessary files...";

    public static void startup() {
        System.out.println("Hello! I'm Mint");
        System.out.println("What can I do for you?");
    }

    public static void shutdown() {
        System.out.println("Goodbye! Hope to see you again soon!");
    }

    public static void printInvalidTagError() {
        System.out.println("Sorry, we are only able to process name, date and process for now");
        System.out.println("The following tags are available: n/ d/ a/");
    }

    public static void help() {
        System.out.println("Available tags: n/name d/date a/amount");
        System.out.println("Order of tags does not matter.");
        System.out.println("List of commands available. "
                + "Square brackets \"[ ]\" identifies an optional argument.");
        System.out.println("- view");
        System.out.println(INDENT + "View expenses");
        System.out.println("- cat");
        System.out.println(INDENT + " View categories and number");
        System.out.println("- add n/NAME a/amount [d/YYYY-MM-DD] [c/catNum]");
        System.out.println(INDENT + "Add expense. Example: add n/chicken rice a/3.50 d/2021-09-30 c/1");
        System.out.println("- delete n/{keyword} [a/amount] [d/YYYY-MM-DD] [c/catNum]");
        System.out.println(INDENT + "Delete expense using keyword search. Example: delete n/chicken");
        System.out.println("- exit");
    }

    public static void viewGivenList(ArrayList<Expense> list) {
        System.out.println("Here is the list of items containing the keyword.");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(INDENT + (i + 1) + "  " + list.get(i).viewToString());
        }
    }

    public static int chooseItemToDeleteOrEdit(ArrayList<Expense> filteredList, boolean isDelete) throws MintException {
        if (isDelete) {
            System.out.println("Enter the index of the item you want to delete." + CANCEL_MESSAGE);
        } else {
            System.out.println("Enter the index of the item you want to edit." + CANCEL_MESSAGE);
        }

        Scanner in = new Scanner(System.in);
        int index = 0;
        boolean proceedToDelete = false;
        while (!proceedToDelete) {
            String userInput = in.nextLine();
            if (userInput.trim().equals("cancel")) {
                System.out.println("Delete process cancelled.");
                return INDEX_CANCEL;
            }
            try {
                index = Integer.parseInt(userInput);
                if (index < 1 || index > filteredList.size()) {
                    System.out.println(MintException.ERROR_INDEX_OUT_OF_BOUND + CANCEL_MESSAGE);
                } else {
                    proceedToDelete = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(MintException.ERROR_INDEX_INVALID_NUMBER + CANCEL_MESSAGE);
            }
        }
        return index - 1;
    }

    public static boolean isConfirmedToDeleteOrEdit(Expense expense, boolean isDelete) {
        if (isDelete) {
            System.out.println("Is this what you want to delete?");
        } else {
            System.out.println("Is this what you want to edit?");
        }
        System.out.println(INDENT + expense);
        System.out.println("Type \"y\" if yes. Type \"n\" if not.");
        Scanner in = new Scanner(System.in);
        while (true) {
            String userInput = in.nextLine();
            switch (userInput.trim()) {
            case "y":
                return true;
            case "n":
                System.out.println("Ok. I have cancelled the delete process.");
                return false;
            default:
                System.out.println("Sorry I don't understand what that means. +"
                        + "Type \"y\" if yes. Type \"n\" if not.");
                break;
            }
        }
    }

    public static void printOutcomeOfEditAttempt(Boolean printEditSuccess, Boolean exceptionThrown) {
        if (!exceptionThrown) {
            if (printEditSuccess) {
                System.out.println(SUCCESSFUL_EDIT_MESSAGE);
            } else {
                System.out.println(UNSUCCESSFUL_EDIT_MESSAGE);
            }
        }
    }

    public static void printMissingFileMessage() {
        System.out.println(MISSING_FILE_MESSAGE);
    }
}



