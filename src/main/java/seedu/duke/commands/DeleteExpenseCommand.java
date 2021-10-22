package seedu.duke.commands;

import seedu.duke.CategoryList;
import seedu.duke.Expense;
import seedu.duke.ExpenseList;
import seedu.duke.MintException;
import seedu.duke.storage.ExpenseListDataManager;

import java.util.ArrayList;
import java.util.logging.Level;

public class DeleteExpenseCommand {

    ExpenseList expenseList = new ExpenseList();

    void deleteExpenseByKeywords(ArrayList<String> tags, String name,
                                 String date, String amount, String catNum,
                                 ArrayList<Expense> dummyExpenseList) throws MintException {
        try {
            Expense expense = expenseList.chooseExpenseByKeywords(tags, true, name, date, amount, catNum);
            if (expense != null) {
                deleteExpense(expense, dummyExpenseList);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    //    public void deleteExpense(Expense expense) throws MintException {
    //        deleteExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }

    public void deleteExpense(Expense expense, ArrayList<Expense> expenseList) throws MintException {
        //        Expense expense = new Expense(name, date, amount, catNum);
        if (expenseList.contains(expense)) {
            //            logger.log(Level.INFO, "User deleted expense: " + expense);
            System.out.println("I have deleted: " + expense);
            expenseList.remove(expense);
            String stringToDelete = ExpenseList.overWriteString(expense);
        //            if (isCurrentMonthExpense(expense)) {
        //                CategoryList.deleteSpending(expense);
        //            }
    //            ExpenseListDataManager.deleteLineInTextFile(stringToDelete);
        }
    }
}
