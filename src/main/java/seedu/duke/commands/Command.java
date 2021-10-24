package seedu.duke.commands;

import seedu.duke.Entry;
import seedu.duke.FinanceManager;
import seedu.duke.Ui;

public abstract class Command {
    public abstract void execute(FinanceManager financeManager, Ui ui);

    public boolean isExit() {
        return false;
    }
}

