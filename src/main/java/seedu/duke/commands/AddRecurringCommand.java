package seedu.duke.commands;

import seedu.duke.*;

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
