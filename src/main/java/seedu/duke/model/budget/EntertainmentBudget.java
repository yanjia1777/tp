package seedu.duke.model.budget;

import seedu.duke.model.entries.ExpenseCategory;

//@@author irvinseet
public class EntertainmentBudget extends Budget {

    public EntertainmentBudget(double limit) {
        this.category = ExpenseCategory.ENTERTAINMENT;
        this.name = ExpenseCategory.ENTERTAINMENT.name();
        this.limit = limit;
    }
}
