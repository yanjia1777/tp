package seedu.duke;

import java.time.LocalDate; // import the LocalDate class
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Entry {
    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");
    public static final int CAT_NUM_OTHERS = 7;
    private int catNum;
    private String name;
    private LocalDate date;
    private double amount;

    public Entry() {
        catNum = CAT_NUM_OTHERS; //others
        date = LocalDate.now();
        amount = 0;
    }

    public Entry(String name, String date, String amount, String catNum) {
        this.catNum = Integer.parseInt(catNum);
        this.name = name;
        this.date = LocalDate.parse(date, dateFormatter);
        this.amount = Double.parseDouble(amount);
    }

    public Entry(String name, String date, String amount) {
        this.catNum = CAT_NUM_OTHERS;
        this.name = name;
        this.date = LocalDate.parse(date, dateFormatter);
        this.amount = Double.parseDouble(amount);
    }

    public String getName() {
        return name;
    }

    public void setDescription(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCatNum() {
        return this.catNum;
    }

    public void setCatNum(int catNum) {
        this.catNum = catNum;
    }

    public double getAmount() {
        return amount;
    }

    public String getAmountString() {
        return Double.toString(amount);
    }

    public String getCat() {
        return CategoryList.getCatName(this.catNum);
    }

    public String getCatIndent() {
        return CategoryList.getCatNameIndented(this.catNum);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
        return getCat() + " | " + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount());
    }

    public String viewToString() {
        return getCatIndent() + "| " + getDate() + " | "
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

        Expense expense = (Expense) object;
        boolean isNameEqual = Objects.equals(name, expense.getName());
        boolean isDateEqual = Objects.equals(date, expense.getDate());
        boolean isAmountEqual = Objects.equals(amount, expense.getAmount());
        boolean isCategoryEqual = Objects.equals(catNum, expense.getCatNum());
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual;
    }
}
