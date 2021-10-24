package seedu.duke.commands;

import seedu.duke.Expense;
import seedu.duke.FinanceManager;
import seedu.duke.Ui;


import java.util.ArrayList;

public class DeleteExpenseCommand extends Command {
    private final ArrayList<String> tags;
    private final Expense expense;

    public DeleteExpenseCommand(ArrayList<String>tags, Expense expense) {
        this.tags = tags;
        this.expense = expense;
    }

    @Override
    public void execute(FinanceManager financeManager, Ui ui) {
        financeManager.deleteExpenseByKeywords(tags,expense);
        ui.printExpensesDeleted(expense);
    }


}
