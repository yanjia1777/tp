package seedu.duke.commands;


import seedu.duke.NormalFinanceManager;
import seedu.duke.RecurringFinanceManager;
import seedu.duke.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
    }

    @Override
    public boolean isExit() {
        return true;
    }

}