package seedu.duke.Model.budget;

import seedu.duke.Model.entries.ExpenseCategory;

//@@author irvinseet
public class FoodBudget extends Budget {

    public FoodBudget(double limit) {
        this.category = ExpenseCategory.FOOD;
        this.name = ExpenseCategory.FOOD.name();
        this.limit = limit;
    }
}
