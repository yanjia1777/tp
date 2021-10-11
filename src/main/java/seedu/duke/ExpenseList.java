package seedu.duke;

import java.util.ArrayList;

public class ExpenseList {
    protected ArrayList<Expense> expenseList = new ArrayList<>();

    public void addExpense(String name, String date, String amount) {
        Expense expense = new Expense(name, date, amount);
        System.out.println("I have added: " + expense);
        expenseList.add(expense);
    }

    public void deleteExpense(String name, String date, String amount) throws MintException {
        Expense expense = new Expense(name, date, amount);
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
            System.out.println(Ui.INDENT + expense.toString());
        }
    }
}
