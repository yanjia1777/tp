package seedu.duke;

import java.util.ArrayList;
import java.time.LocalDate;

public class ExpenseList {
    protected ArrayList<Expense> expenseList = new ArrayList<>();

    public void addExpense(String name, String date, String amount) {
        Expense expense = new Expense(name, date, amount);
        System.out.println("I have added: " + expense.toString());
        expenseList.add(expense);
    }

    public void deleteExpense(String name, String date, String amount) {
        Expense expense = new Expense(name, date, amount);
        if (expenseList.contains(expense)) {
            System.out.println("I have deleted: " + expense.toString());
            expenseList.remove(expenseList.indexOf(expense));
        } else {
            System.out.println("Hmm.. That item is not in the list.");
        }
    }

    public void view() {
        System.out.println("Here is the list of your expenses:");
        for (Expense expense : expenseList) {
            System.out.println(Ui.INDENT + expense.toString());
        }
    }
}
