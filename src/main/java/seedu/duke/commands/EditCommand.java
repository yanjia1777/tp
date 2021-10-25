package seedu.duke.commands;

import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.utility.Ui;

import java.util.ArrayList;

public class EditCommand extends Command {
    private Entry query;
    ArrayList<String> tags;


    public EditCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager, Ui ui) {
        try {
            normalFinanceManager.editEntryByKeywords(tags, query);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
