package seedu.duke.commands;

import seedu.duke.NormalFinanceManager;
import seedu.duke.RecurringFinanceManager;
import seedu.duke.Ui;

public class InvalidCommand extends Command {
    private final String message;

    public InvalidCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
        ui.printInvalidCommand(message);
    }
}