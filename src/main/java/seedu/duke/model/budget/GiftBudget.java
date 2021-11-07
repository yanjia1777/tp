package seedu.duke.model.budget;

import seedu.duke.model.entries.ExpenseCategory;

//@@author irvinseet
public class GiftBudget extends Budget {

    public GiftBudget(double limit) {
        this.category = ExpenseCategory.GIFT;
        this.name = ExpenseCategory.GIFT.name();
        this.limit = limit;
    }
}
