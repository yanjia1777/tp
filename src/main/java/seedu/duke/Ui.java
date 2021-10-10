package seedu.duke;

public class Ui {
    protected static final String INDENT = "    ";

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
        System.out.println("List of commands available. " +
                "Square brackets \"[ ]\" identifies an optional argument.");
        System.out.println("\u2022 view");
        System.out.println("\u2022 add n/NAME a/amount [d/YYYY-MM-DD]");
        System.out.println(INDENT + "Example: add n/chicken rice a/3.50 d/2021-09-30 ");
        System.out.println("\u2022 delete n/NAME a/amount d/YYYY-MM-DD");
        System.out.println(INDENT + "Example: delete n/chicken rice a/3.50 d/2021-09-30");
        System.out.println("\u2022 exit");
    }

}
