package seedu.duke;

public class Ui {
    protected static final String INDENT = "    ";
    public static final String SUCCESSFUL_EDIT_MESSAGE = "Got it! I will update the fields accordingly!";
    public static final String UNSUCCESSFUL_EDIT_MESSAGE = "No difference detected!"
            + "I was unable to perform any edits! "
            + "Please check that you have made changes or included the tags of the fields you wish to edit! :(";

    protected static final String LINE_SEPARATOR = System.lineSeparator();

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

    public static void printOutcomeOfEditAttempt(Boolean printEditSuccess, Boolean exceptionThrown) {
        if (!exceptionThrown) {
            if (printEditSuccess) {
                System.out.println(SUCCESSFUL_EDIT_MESSAGE);
            } else {
                System.out.println(UNSUCCESSFUL_EDIT_MESSAGE);
            }
        }
    }

    public static void printNewUserMessage() {
        System.out.println("No data detected! I see you are a new user...Starting afresh!");
    }
}



