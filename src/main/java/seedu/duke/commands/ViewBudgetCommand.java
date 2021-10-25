package seedu.duke.commands;


import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.utility.Ui;


public class ViewBudgetCommand extends Command {


    public ViewBudgetCommand() {

    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager, Ui ui) {
        ui.printBudgetBreakdown(budgetManager.getBudgetList(), normalFinanceManager.getEntryList());
    }
}


