package seedu.duke;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditExpenseTest {
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";

    @Test
    public void editExpense_editOneVariable_expectSuccess() throws MintException {
        CategoryList.initialiseCategories();
        String input = "d/1999-04-10";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ExpenseList expenseList = new ExpenseList();
        DataManager dataManager = new DataManager(FILE_PATH);
        dataManager.createDirectory();
        expenseList.addExpense("Movie", "2021-12-23", "40", "1");
        expenseList.editExpense("Movie", "2021-12-23", "40", "1");
        String actualOutput = expenseList.getExpenseList().get(0).toString();
        String expectedOutput = "Entertainment | 1999-04-10 | Movie | $40.00";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void editExpense_editTwoVariables_expectSuccess() throws MintException {
        String input = "a/13 n/Movie: Hello";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Movie", "2021-12-23", "40", "1");
        expenseList.editExpense("Movie", "2021-12-23", "40", "1");
        String actualOutput = expenseList.getExpenseList().get(0).toString();
        String expectedOutput = "Entertainment | 2021-12-23 | Movie: Hello | $13.00";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void editExpense_editThreeVariables_expectSuccess() throws MintException {
        String input = "a/8 n/Chicken Rice c/0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Movie", "2021-12-23", "40", "2");
        expenseList.editExpense("Movie", "2021-12-23", "40", "2");
        String actualOutput = expenseList.getExpenseList().get(0).toString();
        String expectedOutput = "Food | 2021-12-23 | Chicken Rice | $8.00";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void editExpense_editFourVariables_expectSuccess() throws MintException {
        String input = "a/8 n/Chicken Rice c/0 d/2000-09-22";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Movie", "2021-12-23", "40", "2");
        expenseList.editExpense("Movie", "2021-12-23", "40", "2");
        String actualOutput = expenseList.getExpenseList().get(0).toString();
        String expectedOutput = "Food | 2000-09-22 | Chicken Rice | $8.00";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void editExpense_invalidDate_expectErrorMessage() throws MintException {
        CategoryList.initialiseCategories();
        String input = "a/";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Movie", "2021-12-23", "40", "1");
        expenseList.editExpense("Movie", "2021-12-23", "40", "1");
        String expectedOutput = "I have added: Entertainment | 2021-12-23 | Movie | $40.00"
                + System.lineSeparator() + "What would you like to edit?"
                + System.lineSeparator() + "Invalid number entered! Unable to edit expense."
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void editExpense_invalidDescription_expectFailure() {
        CategoryList.initialiseCategories();
        String input = "n/";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Movie", "2021-12-23", "40", "1");
        Exception exception = assertThrows(MintException.class,
            () -> expenseList.editExpense("Movie", "2021-12-23", "40", "1"));
        String expectedMessage = "Invalid description entered! Unable to edit expense.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void editExpense_invalidCategoryNumber_expectFailure() {
        CategoryList.initialiseCategories();
        String input = "c/1000";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Movie", "2021-12-23", "40", "1");
        Exception exception = assertThrows(MintException.class,
            () -> expenseList.editExpense("Movie", "2021-12-23", "40", "1"));
        String expectedMessage = "Please enter a valid category number! c/0 to c/7";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}