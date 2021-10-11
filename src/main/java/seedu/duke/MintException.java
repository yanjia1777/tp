package seedu.duke;

public class MintException extends Exception {
    protected static final String ERROR_NO_DELIMETER = "Invalid command entered!";
    protected static final String ERROR_NO_NAME = "Please add the description of the item!";
    protected static final String ERROR_EXPENSE_NOT_IN_LIST = "Hmm.. That item is not in the list.";
    protected static final String ERROR_INVALID_COMMAND = "Sorry I don't know what that means. :(";
    protected static final String ERROR_INVALID_TAG_ERROR = "Sorry, we are only able to process name, date and process for now"
            + Ui.LINE_SEPARATOR + "The following tags are available: n/ d/ a/";

    public MintException(String message) {
        super(message);
    }
}
