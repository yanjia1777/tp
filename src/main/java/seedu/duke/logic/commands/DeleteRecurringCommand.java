package seedu.duke.logic.commands;

import seedu.duke.model.budget.BudgetManager;
import seedu.duke.model.entries.Entry;
import seedu.duke.model.entries.RecurringEntry;
import seedu.duke.utility.MintException;
import seedu.duke.model.financemanager.NormalFinanceManager;
import seedu.duke.model.financemanager.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.ui.Ui;

import java.util.ArrayList;

//@@author pos0414
public class DeleteRecurringCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;
    final boolean isDelete = true;

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
            Entry entryToDelete = dummyDeleteCommand.determineEntryToProcess(recurringFinanceManager, ui, isDelete);
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
