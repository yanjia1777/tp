package seedu.duke.logic.commands;

import seedu.duke.model.budget.BudgetManager;
import seedu.duke.model.entries.Entry;
import seedu.duke.utility.MintException;
import seedu.duke.model.financemanager.FinanceManager;
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
 * Command class used for deleting a normal entry from the list.
 * Includes the smart delete/edit method where the user can easily search for entry to process.
 */
public class DeleteCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;
    static final int SIZE_NO_MATCH = 0;
    static final int SIZE_ONE_MATCH = 1;
    final boolean isDelete = true;

    public DeleteCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    /**
     * Lets the user choose a normal entry to delete by indicating fields. If an entry is chosen,
     * it deletes that normal Entry from the list and deletes the data from the local data file.
     * Prints the outcome of deletion.
     * @param normalFinanceManager Used to delete the entry from the list
     * @param recurringFinanceManager Not used here, for general use in command classes
     * @param budgetManager Not used here, for general use in command classes
     * @param normalListDataManager Used to delete the entry data from local data file
     * @param dataManagerActions Not used here, for general use in command classes
     * @param recurringListDataManager Not used here, for general use in command classes
     * @param budgetDataManager Not used here, for general use in command classes
     * @param ui User interaction point
     */
    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            Entry entryToDelete = determineEntryToProcess(normalFinanceManager, ui, isDelete);
            if (entryToDelete != null) {
                normalFinanceManager.deleteEntry(entryToDelete);
                String stringToDelete = NormalFinanceManager.overWriteString(entryToDelete);
                normalListDataManager.deleteLineInTextFile(stringToDelete);
                ui.printEntryDeleted(entryToDelete);
            }
        } catch (MintException e) {
            ui.printError(e);
        }
    }

    /**
     * Returns an entry to process (delete/edit). Lets the users indicate which fields they want to search
     * and filters the entries that match the query fields. Then they can choose which entry to process.
     * @param financeManager FinanceManager that stores the list of entries
     * @param ui User interaction point for receiving inputs and printing messages
     * @param isDelete True means it is a delete process, false means it is an edit process
     * @return Entry to process (delete/edit)
     * @throws MintException Exception thrown while attempting to choose the entry
     */
    public Entry determineEntryToProcess(FinanceManager financeManager, Ui ui, boolean isDelete) throws MintException {
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            switch (filteredList.size()) {
            case SIZE_NO_MATCH:
                ui.printNoMatchingEntryMessage();
                return null;
            case SIZE_ONE_MATCH:
                return confirmToProcessOneMatch(filteredList, ui, isDelete);
            default:
                return confirmToProcessMultipleMatch(filteredList, ui, isDelete);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    /**
     * Returns the entry to process. Used when there is one matching entry from the user query.
     * Gets confirmation from the user if this is the entry they wish to process (delete/edit).
     * @param filteredList List of matching entries from the user query
     * @param ui User interaction point for receiving inputs and printing messages
     * @param isDelete True means it is a delete process, false means it is an edit process
     * @return Entry to process. If the user decides not to process, it returns null.
     */
    public Entry confirmToProcessOneMatch(ArrayList<Entry> filteredList, Ui ui, boolean isDelete) {
        if (ui.isConfirmedToDeleteOrEdit(filteredList.get(0), isDelete)) {
            return filteredList.get(0);
        } else {
            ui.printCancelMessage();
            return null;
        }
    }

    /**
     * Returns the entry to process. Used when there multiple matching entries from the user query.
     * Lets the user choose the index of the entry they wish to process.
     * @param filteredList List of matching entries from the user query
     * @param ui User interaction point for receiving inputs and printing messages
     * @param isDelete True means it is a delete process, false means it is an edit process
     * @return Entry to process. If the user decides not to process, it returns null.
     */
    public Entry confirmToProcessMultipleMatch(ArrayList<Entry> filteredList, Ui ui, boolean isDelete) {
        ui.viewGivenList(filteredList);
        int index = ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
        if (index >= 0) {
            return filteredList.get(index);
        } else {
            ui.printCancelMessage();
        }
        return null;
    }
}
