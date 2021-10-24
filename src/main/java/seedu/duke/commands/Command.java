package seedu.duke.commands;

import seedu.duke.FinancialManager;
import seedu.duke.Ui;

public abstract class Command {
    public abstract void execute(FinancialManager financialManager, Ui ui);

    public boolean isExit() {
        return false;
    }
}

