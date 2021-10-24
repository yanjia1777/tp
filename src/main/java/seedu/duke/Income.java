package seedu.duke;

import java.time.LocalDate; // import the LocalDate class
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Income extends Entry {
    protected ExpenseCategory category;

    public Income(String name, double amount, ExpenseCategory category) {
        this.name = name;
        this.amount = amount;
        this.date = LocalDate.now();
        this.category = category;
    }

    public Income(String name, LocalDate date, Double amount, ExpenseCategory category) {
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

        Income income = (Income) object;
        boolean isNameEqual = Objects.equals(name, income.name);
        boolean isDateEqual = Objects.equals(date, income.date);
        boolean isAmountEqual = Objects.equals(amount, income.amount);
        boolean isCategoryEqual = Objects.equals(category, income.category);
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual;
    }
}
