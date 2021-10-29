package seedu.duke.parser;

import seedu.duke.exception.MintException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;


public class ViewOptions {
    public String sortType;
    public boolean onlyExpense = false;
    public  boolean onlyIncome = false;
    public LocalDate fromDate = null;
    public LocalDate endDate = null;
    public Month month = null;
    public int year = 0;
    public boolean isViewFrom = false;
    public boolean isViewAll = true;
    public boolean isAscending = false;

    public ViewOptions(String[] argumentArrayInput) throws MintException {

        ArrayList<String>  argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));

        if (argumentArray.contains("expense")) {
            onlyExpense = true;
        }

        if (argumentArray.contains("income")) {
            onlyIncome = true;
        }

        if (argumentArray.contains("by")) {
            try {
                sortType = argumentArray.get(argumentArray.indexOf("by") + 1);
            } catch (IndexOutOfBoundsException e) {
                throw new MintException(MintException.ERROR_INVALID_SORTTYPE);
            }
        }

        if (argumentArray.contains("year")) {
            try {
                year = Integer.parseInt(argumentArray.get(argumentArray.indexOf("year") + 1));
                if (year < 1000 || year > 9999) {
                    throw new MintException(MintException.ERROR_INVALID_YEAR);
                } else {
                    isViewAll = false;
                }
            } catch (IndexOutOfBoundsException e) {
                year = LocalDate.now().getYear();
            }
        }

        if (argumentArray.contains("month")) {
            try {
                month = Month.of(Integer.parseInt(argumentArray.get(argumentArray.indexOf("month") + 1)));
                if (year == 0) {
                    year = LocalDate.now().getYear();
                }
                isViewAll = false;
            } catch (DateTimeException | NumberFormatException e) {
                throw new MintException(MintException.ERROR_INVALID_MONTH);
            } catch (IndexOutOfBoundsException e) {
                month = LocalDate.now().getMonth();
            }
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
                if (endDate == null) {
                    endDate = LocalDate.now();
                }
                isViewAll = false;
            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                throw new MintException(MintException.ERROR_INVALID_DATE);
            }
            isViewAll = false;
        }

        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
            isAscending = true;
        }
    }
}
