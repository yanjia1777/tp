package seedu.duke.commands;


import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.utility.Ui;

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