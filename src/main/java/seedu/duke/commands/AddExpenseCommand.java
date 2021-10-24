package seedu.duke.commands;

import seedu.duke.FinanceManager;
import seedu.duke.Expense;
import seedu.duke.Ui;

public class AddExpenseCommand extends Command {
    private final Expense expense;

    public AddExpenseCommand(Expense expense) {
        this.expense = expense;
    }

    @Override
    public void execute(FinanceManager financeManager, Ui ui) {
        financeManager.addExpense(expense);
        ui.printExpensesAdded(expense);
    }
}
