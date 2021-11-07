package seedu.duke.model.budget;

import seedu.duke.model.entries.ExpenseCategory;

//@@author irvinseet
public class BeautyBudget extends Budget {

    public BeautyBudget(double limit) {
        this.category = ExpenseCategory.BEAUTY;
        this.name = ExpenseCategory.BEAUTY.name();
        this.limit = limit;
    }
}
