package seedu.duke;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;

public class Sorter  {

    public static Comparator<Expense> compareByName = Comparator.comparing(i -> i.getName());

    public static Comparator<Expense> compareByAmount = (i, j) -> (int)(j.getAmount() - i.getAmount());

    public static Comparator<Expense> compareByDate = (i, j) -> j.getDate().compareTo(i.getDate());

    public static Comparator<Expense> compareByCategory = (i, j) -> j.getCategory().compareTo(i.getCategory());

    public static void trimByYear(ArrayList<Expense> outputArray, String year) {
        outputArray.removeIf(expense -> expense.getDate().getYear() != Integer.parseInt(year));
    }

    public static void trimByMonth(ArrayList<Expense> outputArray, Month month) {
        outputArray.removeIf(expense -> expense.getDate().getMonthValue() != month.getValue());
    }

    public static void trimFrom(ArrayList<Expense> outputArray, LocalDate from) {
        outputArray.removeIf(expense -> expense.getDate().isBefore(from));
    }

    public static void trimEnd(ArrayList<Expense> outputArray, LocalDate end) {
        outputArray.removeIf(expense -> expense.getDate().isAfter(end));
    }
}
