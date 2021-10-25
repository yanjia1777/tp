package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

import java.util.ArrayList;

public class BudgetManager {
    ArrayList<Budget> budgetList;
    FoodBudget foodBudget = new FoodBudget(0);
    EntertainmentBudget entertainmentBudget = new EntertainmentBudget(0);
    TransportationBudget transportationBudget = new TransportationBudget(0);
    HouseholdBudget householdBudget = new HouseholdBudget(0);
    ApparelBudget apparelBudget = new ApparelBudget(0);
    BeautyBudget beautyBudget = new BeautyBudget(0);
    GiftBudget giftBudget = new GiftBudget(0);
    OthersBudget othersBudget = new OthersBudget(0);

    public BudgetManager() {
        budgetList = new ArrayList<>();
        budgetList.add(foodBudget);
        budgetList.add(entertainmentBudget);
        budgetList.add(transportationBudget);
        budgetList.add(householdBudget);
        budgetList.add(apparelBudget);
        budgetList.add(beautyBudget);
        budgetList.add(giftBudget);
        budgetList.add(othersBudget);
    }

    public void setBudget(ExpenseCategory category, double amount) {
        Budget budget = getBudgetFromCategory(category);
        budget.setLimit(amount);
    }

    public ArrayList<Budget> getBudgetList() {
        return budgetList;
    }

    private Budget getBudgetFromCategory(ExpenseCategory category) {
        switch (category) {
        case FOOD:
            return foodBudget;
        case ENTERTAINMENT:
            return entertainmentBudget;
        case TRANSPORTATION:
            return transportationBudget;
        case HOUSEHOLD:
            return householdBudget;
        case APPAREL:
            return apparelBudget;
        case BEAUTY:
            return beautyBudget;
        case GIFT:
            return giftBudget;
        default:
            return othersBudget;
        }
    }

}
