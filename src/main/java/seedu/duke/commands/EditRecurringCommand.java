package seedu.duke.commands;

import seedu.duke.*;

import java.util.ArrayList;

public class EditRecurringCommand extends Command {
    private Entry query;
    ArrayList<String> tags;


    public EditRecurringCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
        try {
            recurringFinanceManager.editEntryByKeywords(tags, query);
        } catch (MintException e) {
            ui.printError(e);
        }
    }

}
