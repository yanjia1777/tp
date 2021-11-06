package seedu.duke;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import seedu.duke.entries.Entry;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.Interval;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.RecurringListDataManager;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecurringListDataManagerTest {
    RecurringListDataManager recurringListDataManager = new RecurringListDataManager();
    DataManagerActions dataManagerActions = new DataManagerActions();
    RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    ArrayList<Entry> entryList = recurringFinanceManager.recurringEntryList;
    ClassLoader classLoader = getClass().getClassLoader();
    public static final String RECURRING_FILE_PATH = "data" + File.separator + "MintRecurring.txt";

    @Test
    @Order(1)
    public void loadFromTextFile_loadAllContent_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(RECURRING_FILE_PATH), StandardCharsets.UTF_8));
        recurringListDataManager.loadPreviousFileContents(entryList);
        assertEquals(fileContent.size(), entryList.size());
    }

    @Test
    @Order(2)
    public void appendToTextFile_appendOneEntry_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        String name = "Samurai Burger TestTest sasdassadsd";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        LocalDate endDate = LocalDate.parse("2021-03-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        RecurringExpense expense = new RecurringExpense(name, date, amount, category, interval, endDate);
        recurringListDataManager.appendToMintRecurringListTextFile(expense);
        String expectedOutput = "Expense|0|2021-02-01|Samurai Burger TestTest sasdassadsd|7.5|MONTH|2021-03-01";
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(RECURRING_FILE_PATH), StandardCharsets.UTF_8));
        assertTrue(fileContent.contains(expectedOutput));
    }

    @Test
    @Order(3)
    public void appendToTextFile_deleteOneEntry_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        String name = "Samurai Burger TestTest sasdassadsd";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        LocalDate endDate = LocalDate.parse("2021-03-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        RecurringExpense entryToDelete = new RecurringExpense(name, date, amount, category, interval, endDate);
        String stringToDelete = RecurringFinanceManager.overWriteString(entryToDelete);
        recurringListDataManager.deleteLineInTextFile(stringToDelete);
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(RECURRING_FILE_PATH), StandardCharsets.UTF_8));
        assertFalse(fileContent.contains(stringToDelete));
    }

    @Test
    @Order(4)
    public void deleteInvalidLineInTextFile_deleteOneLine_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        String gibberishToRemove = "sdsadsadsads";
        FileWriter fileWriter = new FileWriter(RECURRING_FILE_PATH, true);
        fileWriter.write(gibberishToRemove);
        fileWriter.flush();
        fileWriter.close();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> recurringListDataManager.loadEntryListContents(entryList));
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(RECURRING_FILE_PATH), StandardCharsets.UTF_8));
        assertFalse(fileContent.contains(gibberishToRemove));
    }
}
