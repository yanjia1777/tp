package seedu.duke.commands;


import seedu.duke.FinancialManager;
import seedu.duke.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(FinancialManager financialManager, Ui ui) {
    }

    @Override
    public boolean isExit() {
        return true;
    }

}