package seedu.duke;

import java.util.Comparator;

public class Sorter extends Expense{

    public static Comparator<Expense> compareByName = new Comparator<Expense>() {
        public int compare(Expense i, Expense j) {
            return i.name.compareTo(j.name);
        }};

    public static Comparator<Expense> compareByAmount = new Comparator<Expense>() {
        public int compare(Expense i, Expense j) {
            return (int)(j.amount - i.amount);
        }};

    public static Comparator<Expense> compareByDate = new Comparator<Expense>() {
        public int compare(Expense i, Expense j) {
            return j.date.compareTo(i.date);
        }};

    public static Comparator<Expense> compareByCategory = new Comparator<Expense>() {
        public int compare(Expense i, Expense j) {
            return j.catNum - i.catNum;
        }};
}
