package seedu.duke.model.budget;

import seedu.duke.model.entries.ExpenseCategory;

//@@author irvinseet
public class TransportationBudget extends Budget {

    public TransportationBudget(double limit) {
        this.category = ExpenseCategory.TRANSPORTATION;
        this.name = ExpenseCategory.TRANSPORTATION.name();
        this.limit = limit;
    }
}
