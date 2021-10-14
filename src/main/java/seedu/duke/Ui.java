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
    public static final String SUCCESSFUL_EDIT_MESSAGE = "Got it! I will update the fields accordingly!";
    public static final String UNSUCCESSFUL_EDIT_MESSAGE = "No difference detected!"
            + "I was unable to perform any edits! "
            + "Please check that you have made changes or included the tags of the fields you wish to edit! :(";

    protected static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String CAT_STR_FOOD = "      Food      ";
    public static final String CAT_STR_ENTERTAINMENT = " Entertainment  ";
    public static final String CAT_STR_TRANSPORTATION = " Transportation ";
    public static final String CAT_STR_HOUSEHOLD = "   Household    ";
    public static final String CAT_STR_APPAREL = "    Apparel     ";
    public static final String CAT_STR_BEAUTY = "     Beauty     ";
    public static final String CAT_STR_GIFT = "      Gift      ";
    public static final String CAT_STR_OTHERS = "     Others     ";
    public static final String CAT_STR_NO_CAT_FOUND = "No category found";

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

    public static String printIndividualCategory(int catNum) {
        switch (catNum) {
        case CAT_NUM_FOOD:
            return "Food";
        case CAT_NUM_ENTERTAINMENT:
            return "Entertainment";
        case CAT_NUM_TRANSPORTATION:
            return "Transportation";
        case CAT_NUM_HOUSEHOLD:
            return "Household";
        case CAT_NUM_APPAREL:
            return "Apparel";
        case CAT_NUM_BEAUTY:
            return "Beauty";
        case CAT_NUM_GIFT:
            return "Gift";
        case CAT_NUM_OTHERS:
            return "Others";
        default:
            return CAT_STR_NO_CAT_FOUND;
        }
    }

    public static String printIndividualCategoryIndent(int catNum) {
        switch (catNum) {
        case CAT_NUM_FOOD:
            return CAT_STR_FOOD;
        case CAT_NUM_ENTERTAINMENT:
            return CAT_STR_ENTERTAINMENT;
        case CAT_NUM_TRANSPORTATION:
            return CAT_STR_TRANSPORTATION;
        case CAT_NUM_HOUSEHOLD:
            return CAT_STR_HOUSEHOLD;
        case CAT_NUM_APPAREL:
            return CAT_STR_APPAREL;
        case CAT_NUM_BEAUTY:
            return CAT_STR_BEAUTY;
        case CAT_NUM_GIFT:
            return CAT_STR_GIFT;
        case CAT_NUM_OTHERS:
            return CAT_STR_OTHERS;
        default:
            return CAT_STR_NO_CAT_FOUND;
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
        System.out.println("def c/0.  Others");
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
}



