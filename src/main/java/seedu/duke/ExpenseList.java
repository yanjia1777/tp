package seedu.duke;

import java.util.ArrayList;

public class ExpenseList {
    protected ArrayList<Expense> expenseList = new ArrayList<>();

    public void addExpense(String name, String date, String amount) {
        Expense expense = new Expense(name, date, amount);
        System.out.println("I have added: " + expense.toString());
        expenseList.add(expense);
    }

    public void deleteExpense() {
        //gimin add here
    }

    public void view () {
        //gimin add here
    }
}
