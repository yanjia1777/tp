package seedu.duke;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AddFunctionTest {

    @Test
    public void addExpense_oneAddition_expectSuccess() {
        LocalDate date = LocalDate.now();
        ExpenseList expenseList = new ExpenseList();
        Expense expense = new Expense("burger", date.toString(), "10");
        expenseList.addExpense(expense.getName(), expense.getDate().toString(), Double.toString(expense.getAmount()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        expenseList.view();
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator() +
                Ui.INDENT + "burger | 2021-10-10 | $10.00" + System.lineSeparator();// Notice the \n for new line.
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void addExpense_twoAdditions_expectSuccess() {
        LocalDate date = LocalDate.now();
        ExpenseList expenseList = new ExpenseList();
        Expense expenseFood = new Expense("burger", date.toString(), "10");
        Expense expenseEntertainment = new Expense("movie", date.toString(), "13");
        expenseList.addExpense(expenseFood.getName(), expenseFood.getDate().toString(), Double.toString(expenseFood.getAmount()));
        expenseList.addExpense(expenseEntertainment.getName(), expenseEntertainment.getDate().toString(), Double.toString(expenseEntertainment.getAmount()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        expenseList.view();
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator() +
                Ui.INDENT + "burger | 2021-10-10 | $10.00" + System.lineSeparator() +
                Ui.INDENT + "movie | 2021-10-10 | $13.00" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void addExpense_wrongAmountFormat_expectErrorMessage() {
        LocalDate date = LocalDate.now();
        ExpenseList expenseList = new ExpenseList();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        expenseList.addExpense("burger", date.toString(), "ABCD");
        // After this all System.out.println() statements will come to outContent stream.
        String expectedOutput  = "Please enter a valid amount!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }


    @Test
    public void addExpense_wrongDateFormat_expectErrorMessage() {
        ExpenseList expenseList = new ExpenseList();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        expenseList.addExpense("movie", "ABCD", "10");
        // After this all System.out.println() statements will come to outContent stream.
        String expectedOutput  = "Please enter a valid date!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}