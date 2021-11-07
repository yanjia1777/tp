package seedu.duke.ui;

import seedu.duke.model.entries.Entry;
import seedu.duke.model.entries.Expense;
import seedu.duke.model.entries.ExpenseCategory;
import seedu.duke.model.entries.Income;
import seedu.duke.model.entries.IncomeCategory;
import seedu.duke.model.entries.Interval;
import seedu.duke.model.entries.RecurringExpense;

import java.time.LocalDate;
import java.util.ArrayList;

public class UiStub extends Ui {

    @Override
    public int chooseItemToDeleteOrEdit(ArrayList<Entry> filteredList, boolean isDelete) {
        LocalDate date = LocalDate.parse("2021-10-17");

        Expense entry1 = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        if (filteredList.contains(entry1)) {
            return 0;
        }

        Income entry2 = new Income("bonus", date, 3.9, IncomeCategory.SALARY);
        if (filteredList.contains(entry2)) {
            return 1;
        }

        Income entry3 = new Income("bonus", date, 3.9, IncomeCategory.ALLOWANCE);
        if (filteredList.contains(entry3)) {
            return 0;
        }

        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense entry4 = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        if (filteredList.contains(entry4)) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean isConfirmedToDeleteOrEdit(Entry entry, boolean isDelete) {
        LocalDate date = LocalDate.parse("2021-10-17");

        Expense entry1 = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        if (entry.equals(entry1)) {
            return true;
        }

        Expense entry2 = new Expense("Nada de coco", date, 3.9, ExpenseCategory.FOOD);
        if (entry.equals(entry2)) {
            return true;
        }

        Expense entry3 = new Expense("jelly", date, 3.9, ExpenseCategory.FOOD);
        if (entry.equals(entry3)) {
            return true;
        }

        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense entry4 = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);

        if (entry.equals(entry4)) {
            return true;
        }
        return false;
    }
}
