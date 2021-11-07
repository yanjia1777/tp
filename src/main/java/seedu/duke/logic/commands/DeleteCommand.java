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

    public Entry confirmToProcessOneMatch(ArrayList<Entry> filteredList, Ui ui, boolean isDelete) {
        if (ui.isConfirmedToDeleteOrEdit(filteredList.get(0), isDelete)) {
            return filteredList.get(0);
        } else {
            ui.printCancelMessage();
            return null;
        }
    }

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
