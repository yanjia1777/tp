package seedu.duke.budget;

import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Type;

import java.time.LocalDate;
import java.util.ArrayList;


public abstract class Budget {
    protected ExpenseCategory category;
    protected String name;
    protected double limit;

    public ExpenseCategory getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public void setLimit(double amount) {
        this.limit = amount;
    }

    public double getLimit() {
        return this.limit;
    }

    public double getMonthlySpending(ArrayList<Entry> entries) {
        double amount = 0;
        for (Entry entry : entries) {
            if (entry.getType() == Type.Expense
                    && (entry.getCategory() == this.category)
                    && (entry.getDate().getMonth() == LocalDate.now().getMonth())
                    && entry.getDate().getYear() == LocalDate.now().getYear()) {
                amount += entry.getAmount();
            }
        }
        return amount;
    }
}
