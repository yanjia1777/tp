package seedu.duke.Model.budget;

import seedu.duke.Model.entries.ExpenseCategory;

//@@author irvinseet
public class TransportationBudget extends Budget {

    public TransportationBudget(double limit) {
        this.category = ExpenseCategory.TRANSPORTATION;
        this.name = ExpenseCategory.TRANSPORTATION.name();
        this.limit = limit;
    }
}
