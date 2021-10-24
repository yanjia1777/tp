package seedu.duke.commands;

import seedu.duke.Expense;
import seedu.duke.FinancialManager;
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
    public void execute(FinancialManager financialManager, Ui ui) {
        financialManager.deleteExpenseByKeywords(tags,expense);
        ui.printExpensesDeleted(expense);
    }


}