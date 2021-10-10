package seedu.duke;
import static org.junit.jupiter.api.Assertions.*;
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

        expenseList.expenseList.add(new Expense(name, date.toString(), amount));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expenseList.deleteExpense(name, date.toString(), amount);
        String expectedOutput  = "I have deleted: Cheese burger | 2021-12-23 | $15.50"
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void deleteExpense_nonExistingItem_exceptionThrown() {
        try {
            ExpenseList expenseList = new ExpenseList();

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            expenseList.deleteExpense("Cheese burger", "2021-12-23", "15.5");
            String wrongExpectedOutput  = "I have deleted: Cheese burger | 2021-12-23 | $15.50"
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

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expenseList.deleteExpense("Cheese burger", "2021-12-23", "15.5abc");
        String expectedOutput  = "Please enter a valid amount!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void deleteExpense_wrongDateFormat_printErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expenseList.deleteExpense("Cheese burger", "2021-12-2", "15.5");
        String ExpectedOutput  = "Please enter a valid date!" + System.lineSeparator();
        assertEquals(ExpectedOutput, outContent.toString());
    }

    @Test
    public void viewExpense_twoExpenses_success() {
        ExpenseList expenseList = new ExpenseList();

        expenseList.expenseList.add(new Expense("Cheese burger", "2021-12-23", "15.5"));
        expenseList.expenseList.add(new Expense("book", "2022-12-23", "9"));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expenseList.viewExpense();
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator() +
                Ui.INDENT + "Cheese burger | 2021-12-23 | $15.50" + System.lineSeparator() +
                Ui.INDENT + "book | 2022-12-23 | $9.00" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void viewExpense_zeroExpenses_success() {
        ExpenseList expenseList = new ExpenseList();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expenseList.viewExpense();
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}