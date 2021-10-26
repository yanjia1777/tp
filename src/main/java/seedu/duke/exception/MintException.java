package seedu.duke.exception;

public class MintException extends Exception {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE_EDIT = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String ERROR_INVALID_SORTTYPE = "Please input how you want the list to be sorted.";
    public static final String ERROR_NO_DELIMETER = "Invalid command entered!";
    public static final String ERROR_NO_NAME = "Please add the description of the item!";
    public static final String ERROR_EXPENSE_NOT_IN_LIST = "Hmm.. That item is not in the list.";
    public static final String ERROR_INVALID_COMMAND = "Sorry I don't know what that means. :(";
    public static final String ERROR_INVALID_TAG_ERROR = "Sorry, we are only able to process name, date, category, and"
            + " amount for normal entries:" + LINE_SEPARATOR + "n/ d/ a/ c/" + LINE_SEPARATOR + "For recurring entries,"
            + " name, date, category, amount, interval, and end date are available:" + LINE_SEPARATOR
            + "n/ d/ a/ c/ i/ e/";
    public static final String ERROR_INDEX_INVALID_NUMBER = "Please enter a valid number.";
    public static final String ERROR_INDEX_OUT_OF_BOUND = "Please enter a number between "
            + "0 and total number of items listed.";
    public static final String ERROR_INVALID_AMOUNT = "Please enter a valid amount!";
    public static final String ERROR_INVALID_DATE = "Please enter a valid date!";
    public static final String ERROR_INVALID_CATNUM_INCOME= "Please enter a valid category number! c/0 to c/4";
    public static final String ERROR_INVALID_CATNUM_EXPENSE = "Please enter a valid category number! c/0 to c/7 for expenses, c/0 to c/4 for income";
    public static final String ERROR_POSITIVE_NUMBER = "Amount cannot be negative";

    public MintException(String message) {
        super(message);
    }
}
