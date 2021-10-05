package seedu.duke;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ExpenseList {
    protected ArrayList<Expense> expenseList = new ArrayList<>();

    public void addExpense(String name, String date, String amount) {
        try {
            Expense expense = new Expense(name, date, amount);
            System.out.println("I have added: " + expense);
            expenseList.add(expense);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount!");
        } catch (DateTimeParseException e) {
            System.out.println("Please enter a valid date!");
        }
    }

    public void deleteExpense(String name, String date, String amount) {
        Expense expense = new Expense(name, date, amount);
        if (expenseList.contains(expense)) {
            System.out.println("I have deleted: " + expense);
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
