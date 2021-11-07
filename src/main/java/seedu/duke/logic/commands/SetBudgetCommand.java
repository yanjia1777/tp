package seedu.duke.logic.commands;


import seedu.duke.model.budget.BudgetManager;
import seedu.duke.model.entries.ExpenseCategory;
import seedu.duke.model.financemanager.NormalFinanceManager;
import seedu.duke.model.financemanager.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.ui.Ui;

//@@author irvinseet
public class SetBudgetCommand extends Command {
    private final ExpenseCategory category;
    private final double amount;

    public SetBudgetCommand(ExpenseCategory category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        assert (amount < 1000000 && amount >= 0);
        budgetManager.setBudget(category, amount);
        budgetDataManager.writeToBudgetTextFile(budgetManager.getBudgetList());
        ui.printSetBudget(category, amount);
    }
}


