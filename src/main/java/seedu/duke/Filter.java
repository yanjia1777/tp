package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Filter {
    public static ArrayList<Expense> filterExpenseByName(String name, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getName().contains(name)).collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Expense> filterExpenseByDate(String date, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getDate().equals(LocalDate.parse(date))).collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Expense> filterExpenseByAmount(String amount, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getAmount() == Double.parseDouble(amount)).collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Expense> filterExpenseByCatNum(String catNum, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getCatNum() == Integer.parseInt(catNum)).collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }
}
