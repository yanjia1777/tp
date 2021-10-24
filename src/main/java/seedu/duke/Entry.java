package seedu.duke;

import java.time.LocalDate; // import the LocalDate class

public abstract class Entry {
    protected String name;
    protected LocalDate date;
    protected double amount;
    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");
    //public static final int CAT_NUM_OTHERS = 7;
    //private int catNum;
    protected Type type;


    public String getName() {
        return name;
    }

    public String getNameIndented() {
        double length = name.length();
        int leftIndent = (int) Math.floor((16 - length) / 2);
        int rightIndent = (int) Math.ceil((16 - length) / 2);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return Ui.getIndent(leftIndent, rightIndent, name).toString();
    }
  
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Type getType() {
        return type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
        return getType() + " | " + getCatIndent() + " | " + getDate() + " | "
                + getNameIndented() + " | $" + String.format("%,.2f", getAmount());
    }

    public abstract Enum getCategory();

    public abstract boolean equals(Object object);

}
