package seedu.duke.model.budget;

import seedu.duke.model.entries.ExpenseCategory;

//@@author irvinseet
public class OthersBudget extends Budget {

    public OthersBudget(double limit) {
        this.category = ExpenseCategory.OTHERS;
        this.name = ExpenseCategory.OTHERS.name();
        this.limit = limit;
    }
}
