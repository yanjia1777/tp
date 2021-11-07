package seedu.duke.Logic.commands;


import seedu.duke.Model.budget.BudgetManager;
import seedu.duke.Model.entries.ExpenseCategory;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Storage.BudgetDataManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;
import seedu.duke.Ui.Ui;

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


