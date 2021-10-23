package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class Filter {
    public static ArrayList<Entry> filterEntryByName(String name, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Entry> filterEntryByDate(String date, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getDate().equals(LocalDate.parse(date, Entry.dateFormatter)))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Entry> filterEntryByAmount(String amount, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getAmount() == Double.parseDouble(amount))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Entry> filterEntryByCatNum(String catNum, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getCatNum() == Integer.parseInt(catNum))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }
}
