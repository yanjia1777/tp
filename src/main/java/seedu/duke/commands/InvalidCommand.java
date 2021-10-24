package seedu.duke.commands;

import seedu.duke.FinanceManager;
import seedu.duke.Ui;

public class InvalidCommand extends Command {
    private final String message;

    public InvalidCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(FinanceManager financeManager, Ui ui) {
        ui.printInvalidCommand(message);
    }
}