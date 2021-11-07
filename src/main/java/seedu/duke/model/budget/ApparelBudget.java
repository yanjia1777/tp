package seedu.duke.model.budget;

import seedu.duke.model.entries.ExpenseCategory;

//@@author irvinseet
public class ApparelBudget extends Budget {

    public ApparelBudget(double limit) {
        this.category = ExpenseCategory.APPAREL;
        this.name = ExpenseCategory.APPAREL.name();
        this.limit = limit;
    }
}
