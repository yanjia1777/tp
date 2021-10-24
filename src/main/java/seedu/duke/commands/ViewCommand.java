package seedu.duke.commands;


import seedu.duke.NormalFinanceManager;
import seedu.duke.MintException;
import seedu.duke.Ui;
import seedu.duke.RecurringFinanceManager;

public class ViewCommand extends Command {
    private String[] argumentArrayInput;

    public ViewCommand(String[] argumentArrayInput) {
        this.argumentArrayInput = argumentArrayInput;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
        try {
            normalFinanceManager.view(argumentArrayInput, recurringFinanceManager);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
