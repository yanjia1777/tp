package seedu.duke.commands;


import seedu.duke.budget.BudgetManager;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.exception.MintException;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;
import seedu.duke.finances.RecurringFinanceManager;

public class ViewCommand extends Command {
    private String[] argumentArrayInput;

    public ViewCommand(String[] argumentArrayInput) {
        this.argumentArrayInput = argumentArrayInput;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
                        NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
                        RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            normalFinanceManager.view(argumentArrayInput, recurringFinanceManager);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
