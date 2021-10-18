package seedu.duke;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;

public class Sorter extends Expense {

    public static Comparator<Expense> compareByName = Comparator.comparing(i -> i.name);

    public static Comparator<Expense> compareByAmount = (i, j) -> (int)(j.amount - i.amount);

    public static Comparator<Expense> compareByDate = (i, j) -> j.date.compareTo(i.date);

    public static Comparator<Expense> compareByCategory = (i, j) -> j.catNum - i.catNum;

    public static void trimByYear(ArrayList<Expense> outputArray, String year) {
        outputArray.removeIf(expense -> expense.date.getYear() != Integer.parseInt(year));
    }

    public static void trimByMonth(ArrayList<Expense> outputArray, Month month) {
        outputArray.removeIf(expense -> expense.date.getMonthValue() != month.getValue());
    }

    public static void trimFrom(ArrayList<Expense> outputArray, LocalDate from) {
        outputArray.removeIf(expense -> expense.date.isBefore(from));
    }

    public static void trimEnd(ArrayList<Expense> outputArray, LocalDate end) {
        outputArray.removeIf(expense -> expense.date.isAfter(end));
    }
}
