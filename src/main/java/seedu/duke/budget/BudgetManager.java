package seedu.duke.budget;

import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Type;
import seedu.duke.storage.BudgetDataManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
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
    public static final String NORMAL_FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String RECURRING_FILE_PATH = "data" + File.separator + "MintRecurring.txt";
    public static final String BUDGET_FILE_PATH = "data" + File.separator + "MintBudget.txt";

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

    public boolean isExceedBudget(Entry entry, ArrayList<Entry> entries) {
        if (entry.getType() == Type.Income) return false;
        else {
            Expense expense = (Expense) entry;
            Budget budget = getBudgetFromCategory(expense.getCategory());
            return getMonthlySpendingCategory(expense.getCategory(), entries) > 0.8 * budget.getLimit();
        }
    }

    public double getMonthlySpendingCategory(ExpenseCategory category, ArrayList<Entry> entries) {
        double amount = 0;
        for (Entry entry : entries) {
            if (entry.getType() == Type.Expense
                    && (entry.getCategory() == category)
                    && (entry.getDate().getMonth() == LocalDate.now().getMonth())
                    && entry.getDate().getYear() == LocalDate.now().getYear()) {
                amount += entry.getAmount();
            }
        }
        return amount;
    }

    public ArrayList<Budget> getBudgetList() {
        return budgetList;
    }

    public Budget getBudgetFromCategory(ExpenseCategory category) {
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
