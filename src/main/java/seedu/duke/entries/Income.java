package seedu.duke.entries;

import seedu.duke.utility.Ui;

import java.time.LocalDate; // import the LocalDate class


public class Income extends Entry {
    protected IncomeCategory category;

    public Income(Income income) {
        super(income);
        this.category = income.getCategory();
        this.type = Type.Income;
    }

    public Income(String name, LocalDate date, double amount, IncomeCategory category) {
        super(name, date, amount);
        this.category = category;
        this.type = Type.Income;
    }

    public IncomeCategory getCategory() {
        return category;
    }

    public void setCategory(IncomeCategory category) {
        this.category = category;
    }

    public String toString() {
        return getType() + "  | " + getCategory().toString() + " | " + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount());
    }

    public String toStringIndented() {
        return getType() + "  | " + getCategoryIndented() + " | " + getDate() + " | "
                + getNameIndented() + " | $" + getAmountIndented();
    }
}
