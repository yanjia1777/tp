package seedu.duke.commands;

import seedu.duke.CategoryList;
import seedu.duke.Expense;
import seedu.duke.ExpenseList;
import seedu.duke.storage.ExpenseListDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class AddExpenseCommand {

    public void addExpense(Expense expense, ArrayList<Expense> expenseList) {
        //        if (isCurrentMonthExpense(expense)) {
        //            CategoryList.addSpending(expense);
        //        }
        //        logger.log(Level.INFO, "User added expense: " + expense);
        System.out.println("I have added: " + expense);
        expenseList.add(expense);
        //        try {
        //            ExpenseListDataManager.appendToExpenseListTextFile(FILE_PATH, expense);
        //        } catch (IOException e) {
        //            System.out.println("Error trying to update external file!");
        //        }
    }
}
