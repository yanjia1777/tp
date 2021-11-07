package seedu.duke;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import seedu.duke.model.budget.Budget;
import seedu.duke.model.budget.BudgetManager;
import seedu.duke.storage.BudgetDataManager;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BudgetDataManagerTest {
    BudgetDataManager budgetDataManager = new BudgetDataManager();
    BudgetManager budgetManager = new BudgetManager();
    ArrayList<Budget> budgetList = budgetManager.getBudgetList();
    public static final String BUDGET_FILE_PATH = "data" + File.separator + "MintBudget.txt";

    @Test
    @Order(1)
    public void writeToBudgetTextFile_writeOneLimit_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        budgetDataManager.writeToBudgetTextFile(budgetList);
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(BUDGET_FILE_PATH), StandardCharsets.UTF_8));
        assertFalse(fileContent.isEmpty());
    }

    @Test
    @Order(2)
    public void loadFromTextFile_loadAllContent_expectSuccess() throws Exception {
        ArrayList<String> fileContent;
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(BUDGET_FILE_PATH), StandardCharsets.UTF_8));
        budgetDataManager.loadFromTextFile(budgetList);
        assertEquals(fileContent.size(), budgetList.size());
    }

    @Test
    @Order(3)
    public void deleteInvalidLineInTextFile_deleteOneLine_expectSuccess() throws Exception {
        String gibberishToRemove = "sdsadsadsads";
        FileWriter fileWriter = new FileWriter(BUDGET_FILE_PATH, true);
        fileWriter.write(gibberishToRemove);
        fileWriter.flush();
        fileWriter.close();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> budgetDataManager.loadBudgetListContents(budgetList));
        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(BUDGET_FILE_PATH),
                StandardCharsets.UTF_8));
        assertFalse(fileContent.contains(gibberishToRemove));
    }
}
