package seedu.duke.Logic.commands;

import seedu.duke.Model.budget.BudgetManager;
import seedu.duke.Model.entries.Entry;
import seedu.duke.Utility.MintException;
import seedu.duke.Model.financeManager.FinanceManager;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Storage.BudgetDataManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;
import seedu.duke.Ui.Ui;

import java.util.ArrayList;

//@@author pos0414
public class DeleteCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;
    public static final int SIZE_NO_MATCH = 0;
    public static final int SIZE_ONE_MATCH = 1;
    boolean isDelete = true;

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
            Entry entryToDelete = determineEntryToDelete(normalFinanceManager, ui);
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

    public Entry determineEntryToDelete(FinanceManager financeManager, Ui ui) throws MintException {
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            switch (filteredList.size()) {
            case SIZE_NO_MATCH:
                ui.printNoMatchingEntryMessage();
                return null;
            case SIZE_ONE_MATCH:
                return confirmToDeleteOneMatch(filteredList, ui);
            default:
                return confirmToDeleteMultipleMatch(filteredList, ui);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public Entry confirmToDeleteOneMatch(ArrayList<Entry> filteredList, Ui ui) {
        if (ui.isConfirmedToDeleteOrEdit(filteredList.get(0), isDelete)) {
            return filteredList.get(0);
        } else {
            ui.printCancelMessage();
            return null;
        }
    }

    public Entry confirmToDeleteMultipleMatch(ArrayList<Entry> filteredList, Ui ui) {
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
