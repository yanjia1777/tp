package seedu.duke.commands;

import seedu.duke.NormalFinanceManager;
import seedu.duke.RecurringFinanceManager;
import seedu.duke.Ui;

public class HelpCommand extends Command{
    public HelpCommand() {
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
        ui.help();
    }
}
