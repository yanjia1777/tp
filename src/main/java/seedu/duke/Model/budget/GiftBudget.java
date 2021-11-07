package seedu.duke.Model.budget;

import seedu.duke.Model.entries.ExpenseCategory;

//@@author irvinseet
public class GiftBudget extends Budget {

    public GiftBudget(double limit) {
        this.category = ExpenseCategory.GIFT;
        this.name = ExpenseCategory.GIFT.name();
        this.limit = limit;
    }
}
