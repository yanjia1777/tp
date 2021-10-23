package seedu.duke;

import seedu.duke.parser.ValidityChecker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class Filter {
    public static ArrayList<Expense> filterExpenseByName(String name, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Expense> filterExpenseByDate(String date, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getDate().equals(LocalDate.parse(date, ValidityChecker.dateFormatter)))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Expense> filterExpenseByAmount(String amount, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getAmount() == Double.parseDouble(amount))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Expense> filterExpenseByCategory(ExpenseCategory category, ArrayList<Expense> listToFilter) {
        ArrayList<Expense> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getCategory() == category)
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }
}
