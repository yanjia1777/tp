package seedu.duke.Logic.commands;

import seedu.duke.Model.budget.BudgetManager;
import seedu.duke.Model.entries.Entry;
import seedu.duke.Model.entries.RecurringEntry;
import seedu.duke.Utility.MintException;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Storage.BudgetDataManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;
import seedu.duke.Ui.Ui;

import java.util.ArrayList;

//@@author pos0414
public class DeleteRecurringCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;

    public DeleteRecurringCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            DeleteCommand dummyDeleteCommand = new DeleteCommand(tags, query);
            Entry entryToDelete = dummyDeleteCommand.determineEntryToDelete(recurringFinanceManager, ui);
            if (entryToDelete != null) {
                recurringFinanceManager.deleteEntry(entryToDelete);
                String stringToDelete = RecurringFinanceManager.overWriteString((RecurringEntry) entryToDelete);
                recurringListDataManager.deleteLineInTextFile(stringToDelete);
                ui.printEntryDeleted(entryToDelete);
            }
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
