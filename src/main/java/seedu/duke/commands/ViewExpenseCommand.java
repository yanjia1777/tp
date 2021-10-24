package seedu.duke.commands;

import seedu.duke.FinancialManager;
import seedu.duke.Ui;

public class ViewExpenseCommand extends Command {
    String[] argumentsArray;

    public ViewExpenseCommand(String[] argumentsArray) {
        this.argumentsArray = argumentsArray;
    }

    @Override
    public void execute(FinancialManager financialManager, Ui ui) {
        financialManager.viewExpense(argumentsArray);
    }
}