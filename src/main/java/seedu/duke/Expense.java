package seedu.duke;


import java.time.LocalDate; // import the LocalDate class
import java.util.Objects;

public class Expense extends Entry {
    protected ExpenseCategory category;

    public Expense(String name, double amount, ExpenseCategory category) {
        this.name = name;
        this.amount = amount;
        this.date = LocalDate.now();
        this.category = category;
    }

    public Expense(String name, LocalDate date, Double amount, ExpenseCategory category) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    @Override
    public ExpenseCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return getCategory() + " | " + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount());
    }

    //@@author nipafx-reusedS
    //Reused from https://www.sitepoint.com/implement-javas-equals-method-correctly/
    //with minor modifications
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }

        Expense expense = (Expense) object;
        boolean isNameEqual = Objects.equals(name, expense.name);
        boolean isDateEqual = Objects.equals(date, expense.date);
        boolean isAmountEqual = Objects.equals(amount, expense.amount);
        boolean isCategoryEqual = Objects.equals(category, expense.category);
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual;
    }
}
