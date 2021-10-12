package seedu.duke;

public class Ui {
    protected static final String INDENT = "    ";
    public static final int CAT_NUM_FOOD = 1;
    public static final int CAT_NUM_ENTERTAINMENT = 2;
    public static final int CAT_NUM_TRANSPORTATION = 3;
    public static final int CAT_NUM_HOUSEHOLD = 4;
    public static final int CAT_NUM_APPAREL = 5;
    public static final int CAT_NUM_BEAUTY = 6;
    public static final int CAT_NUM_GIFT = 7;
    public static final int CAT_NUM_OTHERS = 0;
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
        System.out.println("- add n/NAME a/amount [d/YYYY-MM-DD]");
        System.out.println(INDENT + "Example: add n/chicken rice a/3.50 d/2021-09-30 ");
        System.out.println("- delete n/NAME a/amount d/YYYY-MM-DD");
        System.out.println(INDENT + "Example: delete n/chicken rice a/3.50 d/2021-09-30");
        System.out.println("- exit");
    }

    public static String printIndividualCategory(int catNum) {
        switch (catNum) {
        case CAT_NUM_FOOD:
            return ("      Food      ");
        case CAT_NUM_ENTERTAINMENT:
            return (" Entertainment  ");
        case CAT_NUM_TRANSPORTATION:
            return (" Transportation ");
        case CAT_NUM_HOUSEHOLD:
            return ("   Household    ");
        case CAT_NUM_APPAREL:
            return ("    Apparel     ");
        case CAT_NUM_BEAUTY:
            return ("     Beauty     ");
        case CAT_NUM_GIFT:
            return ("      Gift      ");
        case CAT_NUM_OTHERS:
            return ("     Others     ");
        default:
            return ("No category found");
        }
    }

    public static void printCategories() {
        System.out.println(" Cat no.  Category");
        System.out.println(Ui.INDENT + "c/1.  Food");
        System.out.println(Ui.INDENT + "c/2.  Entertainment");
        System.out.println(Ui.INDENT + "c/3.  Transportation");
        System.out.println(Ui.INDENT + "c/4.  Household");
        System.out.println(Ui.INDENT + "c/5.  Apparel");
        System.out.println(Ui.INDENT + "c/6.  Beauty");
        System.out.println(Ui.INDENT + "c/7.  Gift");
        System.out.println("default c/0  Others");
    }
}



