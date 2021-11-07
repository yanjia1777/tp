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
/**
 * Command class used for deleting a recurring entry from the list.
 */
public class DeleteRecurringCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;

    public DeleteRecurringCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    /**
     * Lets the user choose a recurring entry to delete by indicating fields. If an entry is chosen,
     * it deletes that recurring entry from the list and deletes the data from the local data file.
     * Prints the outcome of deletion.
     * @param normalFinanceManager Not used here, for general use in command classes
     * @param recurringFinanceManager Used to delete the recurring entry from the list
     * @param budgetManager Not used here, for general use in command classes
     * @param normalListDataManager Used to delete the entry data from local data file
     * @param dataManagerActions Not used here, for general use in command classes
     * @param recurringListDataManager Used to delete the recurring entry data from local data file
     * @param budgetDataManager Not used here, for general use in command classes
     * @param ui User interaction point
     */
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
