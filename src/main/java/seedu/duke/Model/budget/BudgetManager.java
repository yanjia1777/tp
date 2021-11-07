package seedu.duke.Model.budget;

import seedu.duke.Model.entries.Entry;
import seedu.duke.Model.entries.Expense;
import seedu.duke.Model.entries.ExpenseCategory;
import seedu.duke.Model.entries.Type;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Model.financeManager.RecurringFinanceManager;
import seedu.duke.Ui.Ui;

import java.io.File;
import java.util.ArrayList;

//@@author irvinseet
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
        Budget budget = getMonthlyBudgetFromCategory(category);
        budget.setLimit(amount);
    }

    public ArrayList<Budget> getBudgetList() {
        return budgetList;
    }

    public Budget getMonthlyBudgetFromCategory(ExpenseCategory category) {
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

    public void checkExceedBudget(Entry entry, NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager, Ui ui) {
        if (entry.getType() == Type.Expense) {
            ArrayList<Entry> entries = normalFinanceManager.getEntryList();
            ArrayList<Entry> recurringEntries = recurringFinanceManager.getCopyOfRecurringEntryList();
            Expense expense = (Expense) entry;
            ExpenseCategory categoryOfCurrentEntry = expense.getCategory();
            Budget budget = budgetManager.getMonthlyBudgetFromCategory(categoryOfCurrentEntry);
            double amountSpent = budget.getMonthlySpending(entries, recurringEntries);
            double spendingLimit = budget.getLimit();
            ui.printBudgetWarningMessage(categoryOfCurrentEntry, amountSpent, spendingLimit);
        }
    }
}
