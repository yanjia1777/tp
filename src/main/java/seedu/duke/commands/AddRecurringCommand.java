package seedu.duke.commands;

import seedu.duke.entries.Entry;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.utility.Ui;

public class AddRecurringCommand extends Command {
    private final Entry entry;

    public AddRecurringCommand(Entry entry) {
        this.entry = entry;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
        try {
            recurringFinanceManager.addEntry(entry);
            ui.printEntryAdded(entry);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
