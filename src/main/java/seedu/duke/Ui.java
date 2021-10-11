package seedu.duke;

public class Ui {
    protected static final String INDENT = "    ";
    protected static final String LINE_SEPARATOR = System.lineSeparator();

    public static void startup() {
        System.out.println("Hello! I'm Mint");
        System.out.println("What can I do for you?");
    }

    public static void shutdown() {
        System.out.println("Goodbye! Hope to see you again soon!");
    }
}
