package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ExpenseListTest {
    @Test
    public void deleteExpense_existingItem_success() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        String name = "Cheese burger";
        String date = "2021-12-23";
        String amount = "15.5";
        String catNum = "1";

        expenseList.expenseList.add(new Expense(name, date, amount, catNum));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expenseList.deleteExpense(name, date, amount, catNum);
        String expectedOutput  = "I have deleted:       Food       | 2021-12-23 | Cheese burger | $15.50"
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void deleteExpense_nonExistingItem_exceptionThrown() {
        try {
            ExpenseList expenseList = new ExpenseList();

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            expenseList.deleteExpense("Cheese burger", "2021-12-23", "15.5", "1");
            String wrongExpectedOutput  = "I have deleted:       Food       | 2021-12-23 | Cheese burger | $15.50"
                    + System.lineSeparator();
            assertEquals(wrongExpectedOutput, outContent.toString());
            fail();
        } catch (MintException e) {
            assertEquals("Hmm.. That item is not in the list.", e.getMessage());
        }
    }

    @Test
    public void deleteExpense_wrongAmountFormat_printErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        parser.executeCommand("delete n/Cheese d/2021-12-23 a/15.5abc c/1", expenseList);
        String expectedOutput  = "Please enter a valid amount!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void deleteExpense_wrongDateFormat_printErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        parser.executeCommand("delete n/Cheese d/2021-12 a/15.5 c/1", expenseList);
        String expectedOutput  = "Please enter a valid date!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void viewExpense_twoExpenses_success() throws MintException {
        ExpenseList expenseList = new ExpenseList();

        expenseList.expenseList.add(new Expense("Cheese burger", "2021-12-23", "15.5"));
        expenseList.expenseList.add(new Expense("book", "2022-12-23", "9"));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] emptyArray = {"view"};
        expenseList.viewExpense(emptyArray);
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator()
                + "      Gift       | 2021-12-23 | Cheese burger | $15.50" + System.lineSeparator()
                + "      Gift       | 2022-12-23 | book | $9.00" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void viewExpense_zeroExpenses_success() throws MintException {
        ExpenseList expenseList = new ExpenseList();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] emptyArray = {"view"};
        expenseList.viewExpense(emptyArray);
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}