package seedu.duke;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ExpenseList {
    protected ArrayList<Expense> expenseList = new ArrayList<>();

    protected static final String ERROR_INVALID_AMOUNT = "Please enter a valid amount!";
    protected static final String ERROR_INVALID_DATE = "Please enter a valid date!";

    public void addExpense(String name, String date, String amount) {
        try {
            Expense expense = new Expense(name, date, amount);
            System.out.println("I have added: " + expense);
            expenseList.add(expense);
        } catch (NumberFormatException e) {
            System.out.println(ERROR_INVALID_AMOUNT);
        } catch (DateTimeParseException e) {
            System.out.println(ERROR_INVALID_DATE);
        }
    }

    public void deleteExpense(String name, String date, String amount) throws MintException {
        try {
            Expense expense = new Expense(name, date, amount);
            if (expenseList.contains(expense)) {
                System.out.println("I have deleted: " + expense);
                expenseList.remove(expenseList.indexOf(expense));
            } else {
                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
            }
        } catch (NumberFormatException e) {
            System.out.println(ERROR_INVALID_AMOUNT);
        } catch (DateTimeParseException e) {
            System.out.println(ERROR_INVALID_DATE);
        }
    }

    public void viewExpense() {
        System.out.println("Here is the list of your expenses:");
        for (Expense expense : expenseList) {
            System.out.println(Ui.INDENT + expense.toString());
        }
    }
}
