package seedu.duke;

import java.time.LocalDate; // import the LocalDate class


public abstract class Entry {
    protected String name;
    protected LocalDate date;
    protected double amount;


    public String getName() {
        return name;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract Enum getCategory();

    public abstract String toString();

    public abstract boolean equals(Object object);

}
