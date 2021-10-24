package seedu.duke.commands;

import seedu.duke.Expense;
import seedu.duke.FinancialManager;
import seedu.duke.Ui;

public class AddExpenseCommand extends Command {
    private final Expense expense;

    public AddExpenseCommand(Expense expense) {
        this.expense = expense;
    }

    @Override
    public void execute(FinancialManager financialManager, Ui ui) {
        financialManager.addExpense(expense);
        ui.printExpensesAdded(expense);
    }
}
