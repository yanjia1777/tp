package seedu.duke.utility;

import seedu.duke.model.entries.Entry;
import seedu.duke.model.entries.RecurringEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

//@@author pos0414

/**
 * Class that deals with filtering out the entries that match the user query of fields.
 */
public class Filter {
    /**
     * Return a list of entries whose names contain queried name string.
     * @param name Queried name string
     * @param listToFilter List of entries to check
     * @return Filtered list of entries whose names contain queried name string
     */
    public static ArrayList<Entry> filterEntryByName(String name, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    /**
     * Return a list of entries whose dates are same as the queried date.
     * @param date Queried date
     * @param listToFilter List of entries to check
     * @return Filtered list of entries whose dates are same as the queried date
     */
    public static ArrayList<Entry> filterEntryByDate(LocalDate date, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    /**
     * Return a list of entries whose end dates are same as the queried date.
     * @param endDate Queried date
     * @param listToFilter List of entries to check
     * @return Filtered list of entries whose end dates are same as the queried date
     */
    public static ArrayList<Entry> filterEntryByEndDate(LocalDate endDate, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> ((RecurringEntry) e).getEndDate().equals(endDate))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    /**
     * Return a list of entries whose intervals are same as the queried interval.
     * @param interval Queried interval
     * @param listToFilter List of entries to check
     * @return Filtered list of entries whose intervals are same as the queried ineterval
     */
    public static ArrayList<Entry> filterEntryByInterval(String interval, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> ((RecurringEntry) e).getInterval().label.equals(interval))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    /**
     * Return a list of entries whose amounts are same as the queried amount(double).
     * @param amount Queried amount (double)
     * @param listToFilter List of entries to check
     * @return Filtered list of entries whose amounts are same as the queried amount
     */
    public static ArrayList<Entry> filterEntryByAmount(Double amount, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getAmount() == amount)
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    /**
     * Return a list of entries whose category numbers are same as the queried category number.
     * @param category Queried category number
     * @param listToFilter List of entries to check
     * @return Filtered list of entries whose category numbers are same as the queried category number
     */
    public static ArrayList<Entry> filterEntryByCategory(int category, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getCategory().ordinal() == category)
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }
}
