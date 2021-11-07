package seedu.duke.model.budget;

import seedu.duke.model.entries.ExpenseCategory;

//@@author irvinseet
public class FoodBudget extends Budget {

    public FoodBudget(double limit) {
        this.category = ExpenseCategory.FOOD;
        this.name = ExpenseCategory.FOOD.name();
        this.limit = limit;
    }
}
