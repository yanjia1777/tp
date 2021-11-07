package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.model.budget.BudgetManager;
import seedu.duke.logic.commands.DeleteRecurringCommand;
import seedu.duke.model.entries.Entry;
import seedu.duke.model.entries.ExpenseCategory;
import seedu.duke.model.entries.Interval;
import seedu.duke.model.entries.RecurringExpense;
import seedu.duke.model.financemanager.NormalFinanceManager;
import seedu.duke.model.financemanager.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.ui.Ui;
import seedu.duke.ui.UiStub;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteRecurringCommandTest {
    RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    ArrayList<Entry> entryList = recurringFinanceManager.recurringEntryList;
    Ui uiStub = new UiStub();
    NormalFinanceManager dummyNormalFinanceManager = new NormalFinanceManager();
    BudgetManager dummyBudgetManager = new BudgetManager();
    NormalListDataManager dummyNormalListDataManager = new NormalListDataManager();
    DataManagerActions dummyDataManagerActions = new DataManagerActions();
    RecurringListDataManager dummyRecurringListDataManager = new RecurringListDataManager();
    BudgetDataManager dummyBudgetDataManager = new BudgetDataManager();


    @Test
    public void execute_multipleExpenseMatch_success() {
        LocalDate date = LocalDate.parse("2021-10-17");
        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense targetEntry = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        RecurringExpense dummyEntry = new RecurringExpense("Nada de coco", date, 5.0, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("coco", null, 0.0, null, null, null);

        DeleteRecurringCommand command = new DeleteRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);
        assertEquals(1, entryList.size());
        assertFalse(entryList.contains(targetEntry));

        entryList.remove(dummyEntry);
    }

    @Test
    public void execute_queryIntervalSingleMatch_deleteTargetSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense targetEntry = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        RecurringExpense dummyEntry = new RecurringExpense("Nada de coco", date, 5.0, ExpenseCategory.FOOD,
                Interval.MONTH, endDate);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("i/");
        Entry query = new RecurringExpense(null, null, 0.0, null, Interval.YEAR, null);

        DeleteRecurringCommand command = new DeleteRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);
        assertEquals(1, entryList.size());
        assertFalse(entryList.contains(targetEntry));

        entryList.remove(dummyEntry);
    }

    @Test
    public void execute_queryEndDateMultipleMatch_deleteTargetSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense targetEntry = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        RecurringExpense dummyEntry = new RecurringExpense("Nada de coco", date, 5.0, ExpenseCategory.FOOD,
                Interval.MONTH, endDate);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("e/");
        Entry query = new RecurringExpense(null, null, 0.0, null, null, endDate);

        DeleteRecurringCommand command = new DeleteRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);
        assertEquals(1, entryList.size());
        assertFalse(entryList.contains(targetEntry));

        entryList.remove(dummyEntry);
    }

    @Test
    public void execute_queryNameAndAmountSingleMatch_deleteTargetSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense targetEntry = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        RecurringExpense dummyEntry = new RecurringExpense("Nada de coco", date, 5.0, ExpenseCategory.FOOD,
                Interval.MONTH, endDate);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        tags.add("a/");
        Entry query = new RecurringExpense("coco", null, 3.9, null, null, endDate);

        DeleteRecurringCommand command = new DeleteRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);
        assertEquals(1, entryList.size());
        assertFalse(entryList.contains(targetEntry));

        entryList.remove(dummyEntry);
    }

}
