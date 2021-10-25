package seedu.duke.commands;


import seedu.duke.budget.BudgetManager;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.exception.MintException;
import seedu.duke.utility.Ui;
import seedu.duke.finances.RecurringFinanceManager;

public class ViewCommand extends Command {
    private String[] argumentArrayInput;

    public ViewCommand(String[] argumentArrayInput) {
        this.argumentArrayInput = argumentArrayInput;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager, Ui ui) {
        try {
            normalFinanceManager.view(argumentArrayInput, recurringFinanceManager);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
