package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.EditRecurringCommand;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Interval;
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

public class EditRecurringExpenseTest {

    RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    Ui uiStub = new UiStub();
    NormalFinanceManager dummyNormalFinanceManager = new NormalFinanceManager();
    ArrayList<Entry> entryList = recurringFinanceManager.recurringEntryList;
    BudgetManager dummyBudgetManager = new BudgetManager();
    NormalListDataManager dummyNormalListDataManager = new NormalListDataManager();
    DataManagerActions dummyDataManagerActions = new DataManagerActions();
    RecurringListDataManager dummyRecurringListDataManager = new RecurringListDataManager();
    BudgetDataManager dummyBudgetDataManager = new BudgetDataManager();

    @Test
    void editRecurringExpense_editOneFieldValid_success() {
        String input = "n/TESTING";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        Double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-12-12", dateFormatter);
        RecurringExpense recurringExpense = new RecurringExpense(name, date, amount, category, interval, endDate);
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        try {
            recurringFinanceManager.addEntry(recurringExpense);
            index = recurringFinanceManager.recurringEntryList.indexOf(recurringExpense);
            recurringFinanceManager.editEntry(recurringExpense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense | FOOD | 2021-02-01 | TESTING |-$7.50 | MONTH | 2021-12-12",
                recurringFinanceManager.recurringEntryList.get(index).toString());
    }

    @Test
    void editRecurringExpense_editTwoFieldsValid_success() {
        String input = "n/TESTING d/2020-12-12";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        Double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-12-12", dateFormatter);
        RecurringExpense recurringExpense = new RecurringExpense(name, date, amount, category, interval, endDate);
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        try {
            recurringFinanceManager.addEntry(recurringExpense);
            index = recurringFinanceManager.recurringEntryList.indexOf(recurringExpense);
            recurringFinanceManager.editEntry(recurringExpense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense | FOOD | 2020-12-12 | TESTING |-$7.50 | MONTH | 2021-12-12",
                recurringFinanceManager.recurringEntryList.get(index).toString());
    }

    @Test
    void editRecurringExpense_editAllFieldsValid_success() {
        String input = "n/TESTING d/2020-12-12 c/7 a/99999 i/YEAR e/2021-02-19";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        Double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-12-12", dateFormatter);
        RecurringExpense recurringExpense = new RecurringExpense(name, date, amount, category, interval, endDate);
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        try {
            recurringFinanceManager.addEntry(recurringExpense);
            index = recurringFinanceManager.recurringEntryList.indexOf(recurringExpense);
            recurringFinanceManager.editEntry(recurringExpense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense | OTHERS | 2020-12-12 | TESTING |-$99,999.00 | YEAR | 2021-02-19",
                recurringFinanceManager.recurringEntryList.get(index).toString());
    }

    @Test
    public void editRecurringExecute_noExpenseMatch_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDate date = LocalDate.parse("2021-10-17");
        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense targetEntry = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("abc", null, 0.0, null,
                null, null);

        EditRecurringCommand command = new EditRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("Hmm.. That item is not in the list." + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void editRecurringExecute_oneExpenseMatch_success() {
        String input = "a/1000";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDate date = LocalDate.parse("2021-10-17");
        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense targetEntry = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("coco", null, 0.0, null,
                null, null);

        EditRecurringCommand command = new EditRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("What would you like to edit? Type the tag and what you want to change e.g. a/10"
                + System.lineSeparator()
                + "Got it! I will update the fields accordingly!"
                + System.lineSeparator(), outContent.toString());
    }


    @Test
    public void editRecurringExecute_multipleExpenseMatch_success() {
        String input = "a/40";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

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
        Entry query = new RecurringExpense("coco", null, 0.0, null,
                null, null);

        EditRecurringCommand command = new EditRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("Here is the list of items containing the keyword."
                + System.lineSeparator()
                + " Index |   Type  | Category |    Date    |     Name     | Amount | Every |   Until"
                + System.lineSeparator()
                + "   1   | Expense |   FOOD   | 2021-10-17 |    Cocoa     |-$3.90  | YEAR  | 2022-10-17"
                + System.lineSeparator()
                + "   2   | Expense |   FOOD   | 2021-10-17 | Nada de coco |-$5.00  | YEAR  | 2022-10-17"
                + System.lineSeparator()
                + "What would you like to edit? "
                + "Type the tag and what you want to change e.g. a/10"
                + System.lineSeparator()
                + "Got it! I will update the fields accordingly!"
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void editRecurringExecute_invalidInput_failure() {
        String input = "a/xxx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDate date = LocalDate.parse("2021-10-17");
        LocalDate endDate = LocalDate.parse("2022-10-17");
        RecurringExpense targetEntry = new RecurringExpense("Cocoa", date, 3.9, ExpenseCategory.FOOD,
                Interval.YEAR, endDate);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new RecurringExpense("coco", null, 0.0, null,
                null, null);

        EditRecurringCommand command = new EditRecurringCommand(tags, query);
        command.execute(dummyNormalFinanceManager, recurringFinanceManager, dummyBudgetManager,
                dummyNormalListDataManager, dummyDataManagerActions, dummyRecurringListDataManager,
                dummyBudgetDataManager, uiStub);

        assertEquals("What would you like to edit? Type the tag and what you want to change e.g. a/10"
                + System.lineSeparator()
                + "Invalid number detected!"
                + System.lineSeparator(), outContent.toString());
    }

}
