package seedu.duke;

import java.time.LocalDate;
import java.util.Objects;

public class RecurringExpense extends Expense {
    private RecurringInterval interval;
    private LocalDate endDate;

    public RecurringExpense() {
        super();
        endDate = LocalDate.parse("2200-12-31");
    }

    public RecurringExpense(RecurringExpense e) {
        setDescription(e.getName());
        setDate(e.getDate());
        setAmount(e.getAmount());
        setCatNum(e.getCatNum());
        setInterval(e.getInterval());
        setEndDate(e.getEndDate());
    }

    public RecurringExpense(String name, String date, String amount,
                            String catNum, String interval, String endDate) throws MintException {
        super(name, date, amount, catNum);
        try {
            setInterval(interval);
            setEndDate(LocalDate.parse(endDate));
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public RecurringInterval getInterval() {
        return interval;
    }

    public void setInterval(RecurringInterval interval) {
        this.interval = interval;
    }

    public void setInterval(String interval) throws MintException {
        switch (interval.toUpperCase()) {
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        return getCat() + " | " + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount()) + " | " + getInterval().label;
    }

    public String viewToString() {
        return getCatIndent()  + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount()) + "| " + getInterval().label + " | ";
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
        boolean isNameEqual = Objects.equals(getName(),recurringExpense.getName());
        boolean isDateEqual = Objects.equals(getDate(), recurringExpense.getDate());
        boolean isAmountEqual = Objects.equals(getAmount(), recurringExpense.getAmount());
        boolean isCategoryEqual = Objects.equals(getCatNum(), recurringExpense.getCatNum());
        boolean isIntervalEqual = Objects.equals(interval, recurringExpense.interval);
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual && isIntervalEqual;
    }
}
