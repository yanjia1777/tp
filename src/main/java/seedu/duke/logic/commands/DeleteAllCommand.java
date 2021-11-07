package seedu.duke.logic.commands;

import seedu.duke.model.budget.BudgetManager;

import seedu.duke.model.financemanager.NormalFinanceManager;
import seedu.duke.model.financemanager.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.ui.Ui;

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