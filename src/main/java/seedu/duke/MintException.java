package seedu.duke;

public class MintException extends Exception {
    protected static final String ERROR_NO_DELIMETER = "Invalid command entered!";
    protected static final String ERROR_NO_DESCRIPTION = "Please add the description of the item!";
    protected static final String ERROR_EXPENSE_NOT_IN_LIST = "Hmm.. That item is not in the list.";
    protected static final String ERROR_INVALID_COMMAND = "Sorry I don't know what that means. :(";

    public MintException(String message) {
        super(message);
    }
}
