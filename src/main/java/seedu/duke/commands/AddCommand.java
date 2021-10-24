package seedu.duke.commands;


import seedu.duke.*;


public class AddCommand extends Command {
    private final Entry entry;

    public AddCommand(Entry entry) {
        this.entry = entry;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
        try {
            normalFinanceManager.addEntry(entry);
            ui.printEntryAdded(entry);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}


