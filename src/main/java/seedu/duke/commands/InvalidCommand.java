package seedu.duke.commands;

import seedu.duke.FinancialManager;
import seedu.duke.Ui;

public class InvalidCommand extends Command {
    private final String message;

    public InvalidCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(FinancialManager financialManager, Ui ui) {
        ui.printInvalidCommand(message);
    }
}