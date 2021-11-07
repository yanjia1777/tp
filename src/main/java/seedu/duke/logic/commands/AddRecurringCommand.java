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

//@@author pos0414
/**
 * Command class used for adding a RecurringEntry to the list.
 */
public class AddRecurringCommand extends Command {
    private final Entry entry;

    public AddRecurringCommand(Entry entry) {
        this.entry = entry;
    }

    /**
     *Adds a recurring to the list and saves the data to local data file.
     * @param normalFinanceManager Not used here, for general use in command classes
     * @param recurringFinanceManager Used to save data for normal entries' list
     * @param budgetManager Not used here, for general use in command classes
     * @param normalListDataManager Not used here, for general use in command classes
     * @param dataManagerActions Not used here, for general use in command classes
     * @param recurringListDataManager Used to save data of the added recurring entry
     * @param budgetDataManager Not used here, for general use in command classes
     * @param ui User interaction point
     */
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
