package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.logic.commands.DeleteCommand;
import seedu.duke.model.entries.Entry;
import seedu.duke.model.entries.Expense;
import seedu.duke.model.entries.ExpenseCategory;
import seedu.duke.model.entries.Income;
import seedu.duke.model.entries.IncomeCategory;
import seedu.duke.utility.MintException;
import seedu.duke.model.financemanager.NormalFinanceManager;
import seedu.duke.ui.Ui;
import seedu.duke.ui.UiStub;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteCommandTest {

    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
    ArrayList<Entry> entryList = normalFinanceManager.entryList;
    Ui uiStub = new UiStub();
    boolean isDelete = true;

    @Test
    public void determineEntryToProcess_multipleExpenseMatch_success() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("Nada de coco", date, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new Expense("coco", null, 0.0, null);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToProcess(normalFinanceManager, uiStub, isDelete);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToProcess_queryCategory_successFindingIncome() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense dummyEntry = new Expense("yam", date, 7.1, ExpenseCategory.FOOD);
        Income targetEntry = new Income("bonus", date, 3.9, IncomeCategory.ALLOWANCE);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("c/");
        //ExpenseCategory.FOOD has same category num as IncomeCategory.SALARY
        Entry query = new Expense(null, null, 0.0, ExpenseCategory.FOOD);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToProcess(normalFinanceManager, uiStub, isDelete);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }


    @Test
    public void determineEntryToProcess_singleExpenseMatch_findTargetSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("jelly", date, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("belly", date, 5.0, ExpenseCategory.OTHERS);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("c/");
        Entry query = new Expense(null, null, 0.0, ExpenseCategory.FOOD);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToProcess(normalFinanceManager, uiStub, isDelete);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToProcess_bothExpenseAndIncomeMatch_findTargetSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense dummyEntry = new Expense("bonus round of burger", date, 3.9, ExpenseCategory.FOOD);
        Income targetEntry = new Income("bonus", date, 3.9, IncomeCategory.SALARY);
        entryList.add(dummyEntry);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new Expense("bonus", null, 0.0, null);


        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToProcess(normalFinanceManager, uiStub, isDelete);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToProcess_cancelSingleMatch_cancelSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("jelly", date, 7.0, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("bean", date, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new Expense("jelly", null, 0.0, null);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToProcess(normalFinanceManager, uiStub, isDelete);
            assertNull(entryToDelete);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToProcess_queryAmountAndCancelMultipleMatch_cancelSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("jelly", date, 100.0, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("bean", date, 100.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a/");
        Entry query = new Expense(null, null, 100.0, null);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToProcess(normalFinanceManager, uiStub, isDelete);
            assertNull(entryToDelete);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

}
