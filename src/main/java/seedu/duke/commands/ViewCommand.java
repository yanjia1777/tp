package seedu.duke.commands;


import seedu.duke.Entry;
import seedu.duke.RecurringExpenseList;
import seedu.duke.Sorter;
import seedu.duke.MintException;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ViewCommand extends Command {

    public void view(String[] argumentArrayInput, RecurringExpenseList recurringExpenseList,
                            ArrayList<Entry> entryList) throws MintException {
        String sortType;
        LocalDate fromDate;
        LocalDate endDate;
        Month month = null;
        String year = null;
        boolean isViewFrom = false;
        boolean isViewAll = true;
        ArrayList<String> argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));
        ArrayList<Entry> outputArray = new ArrayList<>(entryList);

        if (argumentArray.contains("by")) {
            try {
                sortType = argumentArray.get(argumentArray.indexOf("by") + 1);
                sort(outputArray, sortType);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(MintException.ERROR_INVALID_SORTTYPE);
                return;
            }
        }

        System.out.println("Here is the list of your expenses:");

        if (argumentArray.contains("year")) {
            try {
                year = argumentArray.get(argumentArray.indexOf("year") + 1);
            } catch (IndexOutOfBoundsException e) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            System.out.println("For the year " + year + ":");
            Sorter.trimByYear(outputArray, year);
            isViewAll = false;
        }

        if (argumentArray.contains("month")) {
            try {
                month = Month.of(Integer.parseInt(argumentArray.get(argumentArray.indexOf("month") + 1)));
                if (year == null) {
                    year = Integer.toString(LocalDate.now().getYear());
                    Sorter.trimByYear(outputArray, year);
                }
            } catch (IndexOutOfBoundsException e) {
                month = LocalDate.now().getMonth();
            }
            System.out.println("For the month of " + month + ":");
            Sorter.trimByMonth(outputArray, month);
            isViewAll = false;
        }

        if (argumentArray.contains("from")) {
            isViewFrom = true;
            try {
                fromDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 1));
                try {
                    endDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 2));
                } catch (IndexOutOfBoundsException | DateTimeParseException ignored) {
                    endDate = null;
                }
                System.out.print("Since " + fromDate);
                Sorter.trimFrom(outputArray, fromDate);
                if (endDate != null) {
                    Sorter.trimEnd(outputArray, endDate);
                    System.out.print(" to " + endDate);
                } else {
                    endDate = LocalDate.now();
                }
                System.out.println();

            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                System.out.println(MintException.ERROR_INVALID_SORTDATE);
                return;
            }
            recurringExpenseList.viewRecurringExpenseBetweenTwoDates(outputArray, fromDate,
                    endDate);
            isViewAll = false;
        }

        if (!isViewAll && !isViewFrom) {
            if (year == null) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            if (month == null) {
                recurringExpenseList.viewRecurringExpenseByYear(outputArray, Integer.parseInt(year));
            } else {
                recurringExpenseList.viewRecurringExpenseByMonth(outputArray, month.getValue(), Integer.parseInt(year));
            }
        }
        outputArray.sort(Sorter.compareByDate);

        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
            Collections.reverse(outputArray);
        }

        for (Entry entry : outputArray) {
            System.out.println(entry.viewToString());
        }
    }

    public void sort(ArrayList<Entry> outputArray, String sortType) throws MintException {
        assert sortType != null : "sortType should have a command";
        switch (sortType) {
        case "name":
            outputArray.sort(Sorter.compareByName);
            break;
        case "date":
            outputArray.sort(Sorter.compareByDate);
            break;
        case "amount":
            outputArray.sort(Sorter.compareByAmount);
            break;
        case "category":
            outputArray.sort(Sorter.compareByCategory);
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_COMMAND); // Link to MintException
        }
    }

}
