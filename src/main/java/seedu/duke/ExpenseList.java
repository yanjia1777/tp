package seedu.duke;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseList {
    public static final String STRING_PROMPT_EDIT = "What would you like to edit?";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final int LENGTH_OF_SEPARATOR = 2;
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String ERROR_INVALID_SORTTYPE = "Please input how you want the list to be sorted.";
    public static final String ERROR_INVALID_SORTDATE = "Please input a valid date.";
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String REGEX_TO_SPLIT = " ";
    public static final String BLANK = "";
    protected ArrayList<Expense> expenseList = new ArrayList<>();
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void addExpense(String name, String date, String amount, String catNum) {
        Expense expense = new Expense(name, date, amount, catNum);
        logger.log(Level.INFO, "User added expense: " + expense);
        System.out.println("I have added: " + expense);
        expenseList.add(expense);
    }

    public void deleteExpense(String name, String date, String amount, String catNum) throws MintException {
        Expense expense = new Expense(name, date, amount, catNum);
        if (expenseList.contains(expense)) {
            logger.log(Level.INFO, "User deleted expense: " + expense);
            System.out.println("I have deleted: " + expense);
            expenseList.remove(expense);
        } else {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        }
    }

    public void viewExpense(String[] argumentArrayInput) throws MintException {
        String sortType;
        LocalDate fromDate;
        LocalDate endDate;
        Month month;
        String year = null;
        ArrayList<String> argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));
        ArrayList<Expense> outputArray = (ArrayList<Expense>) expenseList.clone();

        if (argumentArray.contains("by")) {
            try {
                sortType = argumentArray.get(argumentArray.indexOf("by") + 1);
                sort(outputArray, sortType);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(ERROR_INVALID_SORTTYPE);
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
                System.out.println(ERROR_INVALID_SORTDATE);
                return;
            }
        }

        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
            Collections.reverse(outputArray);
        }

        for (Expense expense : outputArray) {
            System.out.println(expense.toString());
        }
    }

    public void sort(ArrayList<Expense> outputArray, String sortType) throws MintException {
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
            throw new MintException(MintException.ERROR_INVALID_COMMAND);
        }
    }

    public void editExpense(String name, String date, String amount, String catNum) throws MintException {
        String choice;
        int indexToBeChanged;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;

        try {
            Expense expense = new Expense(name, date, amount, catNum);
            final String originalExpense = expense.toString();
            if (expenseList.contains(expense)) {
                indexToBeChanged = expenseList.indexOf(expense);
                choice = scanFieldsToUpdate();
            } else {
                logger.log(Level.INFO, "User entered invalid entry");
                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
            }
            editSpecifiedEntry(choice, indexToBeChanged, expense);
            printEditSuccess = isEditSuccessful(indexToBeChanged, originalExpense);
        } catch (NumberFormatException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_NUMBER);
        } catch (DateTimeParseException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_DATE);
        }
        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
    }

    private void editSpecifiedEntry(String userInput, int indexToBeChanged, Expense expense) throws MintException {
        Parser parser = new Parser();
        ArrayList<String> splitChoice = new ArrayList<>();
        String choice = " " + userInput;
        while (choice.matches(userTagRaw)) {
            int currentIndex = parser.getCurrentTagIndex(choice);
            int nextIndex = choice.length();

            if (parser.hasNextTag(choice, currentIndex)) {
                nextIndex = parser.getNextTagIndex(choice, currentIndex);
            }
            choice = remainingString(splitChoice, choice, currentIndex, nextIndex);
        }
        amendExpense(indexToBeChanged, splitChoice, expense);
    }

    private String remainingString(ArrayList<String> splitChoice, String choice, int currentIndex, int nextIndex) {
        String description;
        description = choice.substring(currentIndex, nextIndex).trim();
        extractFieldsToAmend(splitChoice, description);
        choice = choice.substring(nextIndex);
        return choice;
    }

    private void extractFieldsToAmend(ArrayList<String> splitChoice, String description) {
        splitChoice.add(description);
    }

    private String scanFieldsToUpdate() {
        String choice;
        Scanner scan = new Scanner(System.in);
        System.out.println(STRING_PROMPT_EDIT);
        choice = scan.nextLine();
        return choice;
    }

    private Boolean isEditSuccessful(int indexToBeChanged, String originalExpense) {
        String newExpense = expenseList.get(indexToBeChanged).toString();
        return !originalExpense.equals(newExpense);
    }

    private void amendExpense(int index, ArrayList<String> choice, Expense expense) throws MintException {
        String name = expense.getName();
        String date = expense.getDate().toString();
        String amount = Double.toString(expense.getAmount());
        String catNum = Integer.toString(expense.getCatNum());
        for (String word : choice) {
            assert (word != null);
            if (word.contains(NAME_SEPARATOR)) {
                name = nonEmptyNewDescription(word);
            }
            if (word.contains(DATE_SEPARATOR)) {
                date = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(AMOUNT_SEPARATOR)) {
                amount = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(CATEGORY_SEPARATOR)) {
                catNum = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
        }
        expenseList.set(index, new Expense(name, date, amount, catNum));
    }

    private String nonEmptyNewDescription(String word) throws MintException {
        String description;
        String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
        if (!newDescription.trim().equalsIgnoreCase(BLANK)) {
            description = newDescription.trim();
        } else {
            throw new MintException(ERROR_INVALID_DESCRIPTION);
        }
        return description;
    }
}
