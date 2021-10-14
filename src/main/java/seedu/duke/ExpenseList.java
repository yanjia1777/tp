package seedu.duke;

import java.time.format.DateTimeParseException;
import java.util.*;

public class ExpenseList {
    public static final String STRING_PROMPT_EDIT = "What would you like to edit?";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final int LENGTH_OF_SEPARATOR = 2;
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String REGEX_TO_SPLIT = " ";
    protected ArrayList<Expense> expenseList = new ArrayList<>();

    public void addExpense(String name, String date, String amount, String catNum) {
        Expense expense = new Expense(name, date, amount, catNum);
        System.out.println("I have added: " + expense);
        expenseList.add(expense);
    }

    public void deleteExpense(String name, String date, String amount, String catNum) throws MintException {
        Expense expense = new Expense(name, date, amount, catNum);
        if (expenseList.contains(expense)) {
            System.out.println("I have deleted: " + expense);
            expenseList.remove(expenseList.indexOf(expense));
        } else {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        }
    }

    public void viewExpense(String[] argumentArray) throws MintException {
        String sortType = "null";
        String modifier = "null";
        ArrayList<Expense> outputArray = (ArrayList<Expense>) expenseList.clone();
        if(argumentArray.length > 1) {
            sortType = argumentArray[1];
        }
        if(argumentArray.length > 2) {
            modifier = argumentArray[2];
        }
        switch (sortType) {
        case "null":
            break;
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
        switch (modifier) {
        case "null":
            break;
        case "ascending":
            //fallthrough
        case "up":
            Collections.reverse(outputArray);
            break;
        case "month":
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
        System.out.println("Here is the list of your expenses:");
        for (Expense expense : outputArray) {
            System.out.println(expense.toString());
        }
    }



    public void editExpense(String name, String date, String amount, String catNum) throws MintException {
        String choice;
        int indexToBeChanged;
        String[] splitChoice;
        Boolean printEditSuccess = false;
        Boolean exceptionThrown = false;

        try {
            Expense expense = new Expense(name, date, amount, catNum);
            final String originalExpense = expense.toString();
            if (expenseList.contains(expense)) {
                indexToBeChanged = expenseList.indexOf(expense);
                Scanner scan = new Scanner(System.in);
                System.out.println(STRING_PROMPT_EDIT);
                choice = scan.nextLine();
            } else {
                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
            }
            splitChoice = choice.split(REGEX_TO_SPLIT);
            edit(indexToBeChanged, splitChoice, name, date, amount, catNum);
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

    private Boolean isEditSuccessful(int indexToBeChanged, String originalExpense) {
        String newExpense = expenseList.get(indexToBeChanged).toString();
        if (!originalExpense.equals(newExpense)) {
            return true;
        }
        return false;
    }

    private void edit(int i, String[] choice, String name, String date, String amt, String cat) throws MintException {
        for (String word : choice) {
            assert (word != null);
            if (word.contains(NAME_SEPARATOR)) {
                name = nonEmptyNewDescription(word);
            }
            if (word.contains(DATE_SEPARATOR)) {
                date = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(AMOUNT_SEPARATOR)) {
                amt = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(CATEGORY_SEPARATOR)) {
                cat = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
        }
        expenseList.set(i, new Expense(name, date, amt, cat));
    }

    private String nonEmptyNewDescription(String word) throws MintException {
        String description;
        String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
        if (!newDescription.trim().equalsIgnoreCase("")) {
            description = newDescription.trim();
        } else {
            throw new MintException(ERROR_INVALID_DESCRIPTION);
        }
        return description;
    }
}
