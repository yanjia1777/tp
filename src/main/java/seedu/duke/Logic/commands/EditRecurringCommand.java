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
