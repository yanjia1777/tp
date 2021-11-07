package seedu.duke.Logic.commands;

import seedu.duke.Model.budget.BudgetManager;

import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Storage.BudgetDataManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import seedu.duke.Storage.RecurringListDataManager;
import seedu.duke.Ui.Ui;

//@@author yanjia1777
public class DeleteAllCommand extends Command {
    private final boolean isNormal;
    private final boolean isRecurring;

    public DeleteAllCommand(boolean isNormal, boolean isRecurring) {
        this.isNormal = isNormal;
        this.isRecurring = isRecurring;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {

        if (ui.isConfirmDeleteAll()) {
            if (isNormal) {
                deleteAllNormal(normalFinanceManager, normalListDataManager);
            }
            if (isRecurring) {
                deleteAllRecurring(recurringFinanceManager, recurringListDataManager);
            }
            ui.deleteAllConfirmation();
        } else {
            ui.deleteAborted();
        }
    }

    public void deleteAllNormal(NormalFinanceManager normalFinanceManager,
            NormalListDataManager normalListDataManager) {
        normalFinanceManager.deleteAll();
        normalListDataManager.deleteAll();
    }

    public void deleteAllRecurring(RecurringFinanceManager recurringFinanceManager,
            RecurringListDataManager recurringListDataManager) {
        recurringFinanceManager.deleteAll();
        recurringListDataManager.deleteAll();
    }
}