package seedu.duke;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static final String BLANK = "";
    protected ArrayList<Expense> expenseList = new ArrayList<>();
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void addExpense(String name, String date, String amount, String catNum) {
        Expense expense = new Expense(name, date, amount, catNum);
        logger.log(Level.INFO, "User added expense: " + expense);
        System.out.println("I have added: " + expense);
        expenseList.add(expense);
    }

    public ArrayList<Expense> filterExpenseByName(String name) {
        List<Expense> filteredList = expenseList
                .stream()
                .filter(e -> e.getName().contains(name))
                .sorted(Comparator.comparing(Expense::getDate))
                .collect(Collectors.toList());
        ArrayList<Expense> filteredArrayList = new ArrayList<Expense>(filteredList);
        return filteredArrayList;
    }

    boolean deleteExpenseByKeyword(String name) throws MintException {
        ArrayList<Expense> filteredList = filterExpenseByName(name);
        if (filteredList.size() == 0) {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        } else if (filteredList.size() == 1) {
            Expense expense = filteredList.get(0);
            deleteExpense(expense.getName(), expense.getDate().toString(),
                    Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
            return true;
        }
        System.out.println("Here is the list of expenses containing the keyword.");
        for (int i = 0; i < filteredList.size(); i++) {
            System.out.println(i + "  " + filteredList.get(i).viewToString());
        }
        System.out.println("Enter the index of the expense you want to delete. To cancel, type \"cancel\"");
        Scanner in = new Scanner(System.in);
        int index = 0;
        boolean proceedToDelete = false;
        while (!proceedToDelete) {
            String userInput = in.nextLine();
            if (userInput.trim().equals("cancel")) {
                System.out.println("Delete process cancelled.");
                return false;
            }
            try {
                index = Integer.parseInt(userInput);
                if (index < 0 || index > filteredList.size()) {
                    throw new MintException("Please enter a number between 0 and total number of expenses shown. "
                            + "To cancel, type \"cancel\"");
                } else {
                    proceedToDelete = true;
                }
            } catch (NumberFormatException e) {
                throw new MintException("Please enter a valid number. To cancel, type \"cancel\"");
            }
        }
        Expense expense = filteredList.get(index);
        deleteExpense(expense.getName(), expense.getDate().toString(),
                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
        return true;
    }


    public void deleteExpense(String name, String date, String amount, String catNum) throws MintException {
        Expense expense = new Expense(name, date, amount, catNum);
        if (expenseList.contains(expense)) {
            logger.log(Level.INFO, "User deleted expense: " + expense);
            System.out.println("I have deleted: " + expense);
            expenseList.remove(expense);
        } else {
            deleteExpenseByKeyword(name);
        }
    }

    public void viewExpense() {
        System.out.println("Here is the list of your expenses:");
        for (Expense expense : expenseList) {
            System.out.println(expense.viewToString());
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

    private void editSpecifiedEntry(String choice, int indexToBeChanged, Expense expense) throws MintException {
        String[] splitChoice;
        splitChoice = choice.split(REGEX_TO_SPLIT);
        amendExpense(indexToBeChanged, splitChoice, expense);
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

    private void amendExpense(int index, String[] choice, Expense expense) throws MintException {
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
