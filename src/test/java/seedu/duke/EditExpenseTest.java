package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.EditCommand;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;
import seedu.duke.utility.UiStub;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

class EditExpenseTest {
    RecurringFinanceManager dummyRecurringFinanceManager = new RecurringFinanceManager();
    Ui uiStub = new UiStub();
    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
    ArrayList<Entry> entryList = normalFinanceManager.entryList;
    BudgetManager dummyBudgetManager = new BudgetManager();
    NormalListDataManager dummyNormalListDataManager = new NormalListDataManager();
    DataManagerActions dummyDataManagerActions = new DataManagerActions();
    RecurringListDataManager dummyRecurringListDataManager = new RecurringListDataManager();
    BudgetDataManager dummyBudgetDataManager = new BudgetDataManager();

    @Test
    void editExpense_editOneFieldValid_success() {
        String input = "n/TESTING";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(expense);
            index = normalFinanceManager.entryList.indexOf(expense);
            normalFinanceManager.editEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense  | FOOD | 2021-02-01 | TESTING | $7.50",
                normalFinanceManager.entryList.get(index).toString());
    }

    @Test
    void editExpense_editTwoFieldsValid_success() {
        String input = "n/TESTING a/99999";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(expense);
            index = normalFinanceManager.entryList.indexOf(expense);
            normalFinanceManager.editEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense  | FOOD | 2021-02-01 | TESTING | $99,999.00",
                normalFinanceManager.entryList.get(index).toString());
    }

    @Test
    void editExpense_editAllFieldsValid_success() {
        String input = "n/TESTING a/99999 d/2021-12-12 c/7";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(expense);
            index = normalFinanceManager.entryList.indexOf(expense);
            normalFinanceManager.editEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense  | OTHERS | 2021-12-12 | TESTING | $99,999.00",
                normalFinanceManager.entryList.get(index).toString());
    }

    //    @Test
    //    public void editExpense_invalidCategoryNumber_expectFailure() {
    //        CategoryList.initialiseCategories();
    //        String input = "c/1000";
    //        InputStream in = new ByteArrayInputStream(input.getBytes());
    //        System.setIn(in);
    //        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    //        System.setOut(new PrintStream(outContent));
    //        ExpenseList expenseList = new ExpenseList();
    //        expenseList.addExpense("Movie", "2021-12-23", "40", "1");
    //        Exception exception = assertThrows(MintException.class,
    //            () -> expenseList.editExpense("Movie", "2021-12-23", "40", "1"));
    //        String expectedMessage = "Please enter a valid category number! c/0 to c/7";
    //        String actualMessage = exception.getMessage();
    //        assertTrue(actualMessage.contains(expectedMessage));
    //    }
    //

    @Test
    public void editExecute_noExpenseMatch_failure() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("abc", null, 0.0, null,
                null, null);

        EditCommand command = new EditCommand(tags, query);
        command.execute(normalFinanceManager, dummyRecurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("Hmm.. That item is not in the list." + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void editExecute_oneExpenseMatch_success() {
        String input = "a/1000";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("coco", null, 0.0, null,
                null, null);

        EditCommand command = new EditCommand(tags, query);
        command.execute(normalFinanceManager, dummyRecurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("What would you like to edit? Type the tag and what you want to change e.g. a/10"
                + System.lineSeparator()
                + "Got it! I will update the fields accordingly!"
                + System.lineSeparator(), outContent.toString());
    }


    @Test
    public void editExecute_multipleExpenseMatch_success() {
        String input = "a/40";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("Nada de coco", date, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("coco", null, 0.0, null,
                null, null);

        EditCommand command = new EditCommand(tags, query);
        command.execute(normalFinanceManager, dummyRecurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("Here is the list of items containing the keyword." + System.lineSeparator()
                + " Index |   Type  | Category |    Date    |     Name     | Amount | Every |   Until"
                + System.lineSeparator()
                + "   1   | Expense |   FOOD   | 2021-10-17 |    Cocoa     |-$3.90  |       |"
                + System.lineSeparator()
                + "   2   | Expense |   FOOD   | 2021-10-17 | Nada de coco |-$5.00  |       |"
                + System.lineSeparator()
                + "What would you like to edit? "
                + "Type the tag and what you want to change e.g. a/10"
                + System.lineSeparator()
                + "Got it! I will update the fields accordingly!"
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void editExecute_invalidInput_failure() {
        String input = "a/xxx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("coco", null, 0.0, null,
                null, null);

        EditCommand command = new EditCommand(tags, query);
        command.execute(normalFinanceManager, dummyRecurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("What would you like to edit? Type the tag and what you want to change e.g. a/10"
                + System.lineSeparator()
                + "Invalid number detected!"
                + System.lineSeparator(), outContent.toString());
    }
}