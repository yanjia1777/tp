package seedu.duke;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import seedu.duke.Model.entries.Entry;
import seedu.duke.Model.entries.Expense;
import seedu.duke.Model.entries.ExpenseCategory;
import seedu.duke.Model.financeManager.NormalFinanceManager;
import seedu.duke.Storage.DataManagerActions;
import seedu.duke.Storage.NormalListDataManager;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.duke.Logic.parser.ValidityChecker.dateFormatter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NormalListDataManagerTest {
    NormalListDataManager normalListDataManager = new NormalListDataManager();
    DataManagerActions dataManagerActions = new DataManagerActions();
    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
    ArrayList<Entry> entryList = normalFinanceManager.entryList;
    ClassLoader classLoader = getClass().getClassLoader();
    public static final String NORMAL_FILE_PATH = "data" + File.separator + "Mint.txt";

    @Test
    @Order(1)
    public void loadFromTextFile_loadAllContent_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH), StandardCharsets.UTF_8));
        normalListDataManager.loadPreviousFileContents(entryList);
        assertEquals(fileContent.size(), entryList.size());
    }

    @Test
    @Order(2)
    public void appendToTextFile_appendOneEntry_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        String name = "Samurai Burger TestTest sasdassadsd";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        normalListDataManager.appendToEntryListTextFile(expense);
        String expectedOutput = "Expense|0|2021-02-01|Samurai Burger TestTest sasdassadsd|7.5";
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH), StandardCharsets.UTF_8));
        assertTrue(fileContent.contains(expectedOutput));
    }

    @Test
    @Order(3)
    public void appendToTextFile_deleteOneEntry_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        String name = "Samurai Burger TestTest sasdassadsd";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense entryToDelete = new Expense(name, date, amount, category);
        String stringToDelete = NormalFinanceManager.overWriteString(entryToDelete);
        normalListDataManager.deleteLineInTextFile(stringToDelete);
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH), StandardCharsets.UTF_8));
        assertFalse(fileContent.contains(stringToDelete));
    }

    @Test
    @Order(4)
    public void deleteInvalidLineInTextFile_deleteOneLine_expectSuccess() throws Exception {
        String gibberishToRemove = "sdsadsadsads";
        FileWriter fileWriter = new FileWriter(NORMAL_FILE_PATH, true);
        fileWriter.write(gibberishToRemove);
        fileWriter.flush();
        fileWriter.close();
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> normalListDataManager.loadEntryListContents(entryList));
        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH),
                StandardCharsets.UTF_8));
        assertFalse(fileContent.contains(gibberishToRemove));
    }
}
