package seedu.duke.commands;

import seedu.duke.Entry;
import seedu.duke.FinancialManager;
import seedu.duke.MintException;
import seedu.duke.Ui;
import seedu.duke.storage.EntryListDataManager;

import java.util.ArrayList;

public class DeleteCommand extends Command {


    public void deleteByKeywords(ArrayList<String> tags, Entry entry, FinancialManager financialManager) throws MintException {
        try {
            Entry finalEntry = financialManager.chooseEntryByKeywords(tags, true, entry);
            if (finalEntry != null) {
                delete(finalEntry, financialManager.entryList);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    //    public void deleteExpense(Expense expense) throws MintException {
    //        deleteExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }

    public void delete(Entry entry, ArrayList<Entry> entryList) throws MintException {
        //        Expense expense = new Expense(name, date, amount, catNum);
        if (entryList.contains(entry)) {
            //            logger.log(Level.INFO, "User deleted expense: " + expense);
            System.out.println("I have deleted: " + entry);
            entryList.remove(entry);
            String stringToDelete = FinancialManager.overWriteString(entry);
            //            if (isCurrentMonthExpense(expense)) {
            //                CategoryList.deleteSpending(expense);
            //            }
            EntryListDataManager.deleteLineInTextFile(stringToDelete);
        }
    }

    @Override
    public void execute(FinancialManager financialManager, Ui ui) {

    }
}
