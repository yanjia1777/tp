package seedu.duke;

import java.util.Objects;

public class RecurringExpense extends Expense {
    RecurringInterval interval;

    public RecurringExpense() {
        super();
    }

    public RecurringExpense(String name, String date, String amount, String catNum, String interval) throws MintException {
        super(name, date, amount, catNum);
        try {
            setInterval(interval);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public RecurringInterval getInterval() {
        return interval;
    }

    public void setInterval(String interval) throws MintException {
        switch (interval.toUpperCase()) {
        case "WEEK":
            this.interval = RecurringInterval.WEEK;
            break;
        case "MONTH":
            this.interval = RecurringInterval.MONTH;
            break;
        case "YEAR":
            this.interval = RecurringInterval.YEAR;
            break;
        default:
            throw new MintException("You entered invalid interval");
        }
    }

    public String toString() {
        return getCat() + " | " + getInterval().label + " | " + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount());
    }

    public String viewToString() {
        return getCatIndent() + "| " + getInterval().label + " | " + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount());
    }

    //@@author nipafx-reusedS
    //Reused from https://www.sitepoint.com/implement-javas-equals-method-correctly/
    //with minor modifications
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

        RecurringExpense recurringExpense = (RecurringExpense) object;
        boolean isNameEqual = Objects.equals(name,recurringExpense.name);
        boolean isDateEqual = Objects.equals(date, recurringExpense.date);
        boolean isAmountEqual = Objects.equals(amount, recurringExpense.amount);
        boolean isCategoryEqual = Objects.equals(catNum, recurringExpense.catNum);
        boolean isIntervalEqual = Objects.equals(interval, recurringExpense.interval);
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual && isIntervalEqual;
    }
}
