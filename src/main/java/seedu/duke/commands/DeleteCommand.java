package seedu.duke.commands;

import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

import java.util.ArrayList;

public class DeleteCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;
    private final boolean isDeleteAll;

    public DeleteCommand(ArrayList<String> tags, Entry query, boolean isDeleteALl) {
        this.query = query;
        this.tags = tags;
        this.isDeleteAll = isDeleteALl;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
                        NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
                        RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            if (isDeleteAll) {
                deleteAll(normalFinanceManager, normalListDataManager);
                return;
            }
            Entry deletedEntry = normalFinanceManager.deleteEntryByKeywords(tags, query);
            String stringToDelete = NormalFinanceManager.overWriteString(deletedEntry);
            normalListDataManager.deleteLineInTextFile(stringToDelete);
            ui.printEntryDeleted(deletedEntry);
        } catch (MintException e) {
            ui.printError(e);
        }
    }

    public void deleteAll(NormalFinanceManager normalFinanceManager, NormalListDataManager normalListDataManager) {
        if (Ui.isConfirmDeleteAll()) {
            normalFinanceManager.deleteAll();
            normalListDataManager.deleteAll();
            Ui.deleteAllConfirmation();
        } else {
            Ui.deleteAborted();
        }
    }
}
