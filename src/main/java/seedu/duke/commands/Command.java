package seedu.duke.commands;

import seedu.duke.NormalFinanceManager;
import seedu.duke.RecurringFinanceManager;
import seedu.duke.Ui;

public abstract class Command {
    public abstract void execute(NormalFinanceManager normalFinanceManager,
                                 RecurringFinanceManager recurringFinanceManager, Ui ui);

    public boolean isExit() {
        return false;
    }
}

