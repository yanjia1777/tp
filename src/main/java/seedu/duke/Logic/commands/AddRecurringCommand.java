package seedu.duke.Logic.commands;

import seedu.duke.Model.budget.BudgetManager;
import seedu.duke.Model.entries.Entry;
import seedu.duke.Utility.MintException;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Storage.BudgetDataManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;
import seedu.duke.Ui.Ui;

//@@author pos0414
public class AddRecurringCommand extends Command {
    private final Entry entry;

    public AddRecurringCommand(Entry entry) {
        this.entry = entry;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            recurringFinanceManager.addEntry(entry);
            recurringListDataManager.appendToMintRecurringListTextFile(entry);
            ui.printEntryAdded(entry);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
