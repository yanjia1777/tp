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
 * Command class used for adding a normal Entry to the list.
 */
public class AddCommand extends Command {
    private final Entry entry;

    public AddCommand(Entry entry) {
        this.entry = entry;
    }

    /**
     * Adds a normal Entry to the list and saves the data to local data file.
     * Checks if the added entry makes it go over spending limit, and print the outcome of addition.
     * @param normalFinanceManager Used to add the entry to the list
     * @param recurringFinanceManager Not used here, for general use in command classes
     * @param budgetManager Used to check the budget
     * @param normalListDataManager Used to save data of the added entry
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
            normalFinanceManager.addEntry(entry);
            normalListDataManager.appendToEntryListTextFile(entry);
            ui.printEntryAdded(entry);
            budgetManager.checkExceedBudget(entry, normalFinanceManager, recurringFinanceManager, budgetManager, ui);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}




