package seedu.duke;

import java.time.LocalDate; // import the LocalDate class

public class Expense {

    public static String[] categories = {"Food", "Entertainment", "Transportation", "Household", "Apparel", "Beauty", "Gift", "Other"};

    private int catNum;
    private String description;
    private LocalDate date;
    private double amount;
    private boolean monthly;

    public Expense() {
        catNum = 7;
        description = "none";
        date = LocalDate.of(2021, 1, 1);
        amount = 0;
        monthly = false;
    }

    public Expense(int catNum, String description, double amount) {
        this.catNum = catNum;
        this.description = description;
        this.date = LocalDate.now();
        this.amount = amount;
        this.monthly = false;
    }

    public String getCat() {
        return categories[this.getCatNum()];
    }

    public int getCatNum() {
        return catNum;
    }

    public void setCatNum(int catNum) {
        this.catNum = catNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

//    mm dd yyyy |      food      | amt |description
//    mm dd yyyy | transportation | amt |description

    public String toString() {
        return getDate() + " | " + getCat() + " | $" + getAmount() + " | " + getDescription() ;
    }

}
