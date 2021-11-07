package seedu.duke.Model.budget;

import seedu.duke.Model.entries.ExpenseCategory;

//@@author irvinseet
public class BeautyBudget extends Budget {

    public BeautyBudget(double limit) {
        this.category = ExpenseCategory.BEAUTY;
        this.name = ExpenseCategory.BEAUTY.name();
        this.limit = limit;
    }
}
