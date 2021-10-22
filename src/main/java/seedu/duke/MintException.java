package seedu.duke;

public class MintException extends Exception {
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String ERROR_INVALID_SORTTYPE = "Please input how you want the list to be sorted.";
    public static final String ERROR_INVALID_SORTDATE = "Please input a valid date.";
    protected static final String ERROR_NO_DELIMETER = "Invalid command entered!";
    protected static final String ERROR_NO_NAME = "Please add the description of the item!";
    protected static final String ERROR_EXPENSE_NOT_IN_LIST = "Hmm.. That item is not in the list.";
    protected static final String ERROR_INVALID_COMMAND = "Sorry I don't know what that means. :(";
    protected static final String ERROR_INVALID_TAG_ERROR = "Sorry, we are only able to process name, date and"
            + " process for now" + Ui.LINE_SEPARATOR + "The following tags are available: n/ d/ a/";
    protected static final String ERROR_INDEX_INVALID_NUMBER = "Please enter a valid number.";
    protected static final String ERROR_INDEX_OUT_OF_BOUND = "Please enter a number between "
            + "0 and total number of items listed.";

    public MintException(String message) {
        super(message);
    }
}
