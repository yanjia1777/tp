package seedu.duke.Model.budget;

import seedu.duke.Model.entries.ExpenseCategory;

//@@author irvinseet
public class OthersBudget extends Budget {

    public OthersBudget(double limit) {
        this.category = ExpenseCategory.OTHERS;
        this.name = ExpenseCategory.OTHERS.name();
        this.limit = limit;
    }
}
