package seedu.duke;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

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

    public void viewExpense() {
        System.out.println("Here is the list of your expenses:");
        for (Expense expense : expenseList) {
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
            String originalExpense = expense.toString();
            if (expenseList.contains(expense)) {
                indexToBeChanged = expenseList.indexOf(expense);
                Scanner scan = new Scanner(System.in);
                System.out.println(STRING_PROMPT_EDIT);
                choice = scan.nextLine();
            } else {
                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
            }
            splitChoice = choice.split(REGEX_TO_SPLIT);
            updateEntryOfExpense(indexToBeChanged, splitChoice, name, date, amount, catNum);
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
        if(!originalExpense.equals(newExpense)) {
            return true;
        }
        return false;
    }

    private void updateEntryOfExpense(int indexToBeChanged, String[] splitChoice, String description, String date, String amount, String catNum) throws MintException {
        for (String word : splitChoice) {
            assert (word != null);
            if (word.contains(NAME_SEPARATOR)) {
                String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
                if(!newDescription.trim().equalsIgnoreCase("")) {
                    description = newDescription.trim();
                } else {
                    throw new MintException(ERROR_INVALID_DESCRIPTION);
                }
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
        expenseList.set(indexToBeChanged, new Expense(description, date, amount, catNum));
    }
}
