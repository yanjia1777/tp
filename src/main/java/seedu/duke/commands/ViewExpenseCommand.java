package seedu.duke.commands;

import seedu.duke.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ViewExpenseCommand {

    public static final String ERROR_INVALID_SORTTYPE = "Please input how you want the list to be sorted.";
    public static final String ERROR_INVALID_SORTDATE = "Please input a valid date.";
    protected static final String ERROR_INVALID_COMMAND = "Sorry I don't know what that means. :(";

    public void viewExpense(String[] argumentArrayInput,
                            RecurringExpenseList recurringExpenseList, ArrayList<Expense> expenseList) throws MintException {
        String sortType;
        LocalDate fromDate;
        LocalDate endDate;
        Month month;
        String year = null;
        ArrayList<String> argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));
        //        ArrayList<Expense> outputArray = new ArrayList<Expense>(expenseList);
        ArrayList<Expense> outputArray = new ArrayList<Expense>(expenseList);


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
            if (year == null) {
                year = Integer.toString(LocalDate.now().getYear());
            }
            //recurringExpenseList.viewRecurringExpenseByMonth(outputArray, month.getValue(),
            //        Integer.parseInt(year));
        }

        if (argumentArray.contains("from")) {
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
                }
                System.out.println();

            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                System.out.println(MintException.ERROR_INVALID_SORTDATE);
                return;
            }
            //recurringExpenseList.viewRecurringExpenseBetweenTwoDates(outputArray, fromDate,
            //        endDate);
        }

        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
            Collections.reverse(outputArray);
        }

        for (Expense expense : outputArray) {
            System.out.println(expense.viewToString());
        }
    }

    public void sort(ArrayList<Expense> outputArray, String sortType) throws MintException {
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
            throw new MintException(ERROR_INVALID_COMMAND); // Link to MintException
        }
    }

}
