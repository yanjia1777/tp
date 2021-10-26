package seedu.duke.commands;


import seedu.duke.budget.BudgetManager;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ViewOptions;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.entries.Entry;

import java.util.ArrayList;
import java.util.Collections;

public class ViewCommand extends Command {
    private final ViewOptions viewOptions;
    protected ArrayList<Entry> outputArray;

    public ViewCommand(ViewOptions viewOptions) {
        this.viewOptions = viewOptions;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
                        NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
                        RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            outputArray = normalFinanceManager.view(viewOptions);
            outputArray = recurringFinanceManager.view(viewOptions, outputArray);

            if (viewOptions.sortType != null) {
                normalFinanceManager.sort(viewOptions.sortType, outputArray);
            }

            if (viewOptions.isAscending) {
                Collections.reverse(outputArray);
            }

            double total = normalFinanceManager.calculateTotal(outputArray);

            ui.printView(outputArray, viewOptions.fromDate, viewOptions.endDate, total);

            if (viewOptions.isViewAll) {
                ui.printViewRecurring(recurringFinanceManager.recurringEntryList);
            }
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
