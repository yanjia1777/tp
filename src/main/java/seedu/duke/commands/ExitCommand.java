package seedu.duke.commands;


import seedu.duke.FinanceManager;
import seedu.duke.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(FinanceManager financeManager, Ui ui) {
    }

    @Override
    public boolean isExit() {
        return true;
    }

}