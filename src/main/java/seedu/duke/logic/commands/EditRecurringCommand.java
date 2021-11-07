package seedu.duke.logic.commands;

import seedu.duke.model.budget.BudgetManager;
import seedu.duke.model.entries.Entry;
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
public class EditRecurringCommand extends Command {
    private Entry query;
    ArrayList<String> tags;


    public EditRecurringCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            EditCommand dummyEditCommand = new EditCommand(tags, query);
            Entry entryToEdit = dummyEditCommand.determineEntryToEdit(recurringFinanceManager, ui);
            if (entryToEdit != null) {
                ArrayList<String> list = recurringFinanceManager.editEntry(entryToEdit);
                String stringToOverwrite = list.get(0);
                String stringToUpdate = list.get(1);
                recurringListDataManager.editEntryListTextFile(stringToOverwrite, stringToUpdate);
            }
        } catch (MintException e) {
            ui.printError(e);
        }
    }

}
