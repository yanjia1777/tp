package seedu.duke;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddFunctionTest {

    @Test
    public void addExpense_oneAddition_expectSuccess() {
        LocalDate date = LocalDate.now();
        ExpenseList expenseList = new ExpenseList();
        Expense expense = new Expense("burger", "2021-10-10", "10");
        String expenseName = expense.getName();
        String expenseDate = expense.getDate().toString();
        String expenseAmount = Double.toString(expense.getAmount());
        expenseList.addExpense(expenseName, expenseDate, expenseAmount);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        expenseList.viewExpense();
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator()
                + Ui.INDENT + "burger | 2021-10-10 | $10.00" + System.lineSeparator();// Notice the \n for new line.
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void addExpense_twoAdditions_expectSuccess() {
        LocalDate date = LocalDate.now();
        ExpenseList expenseList = new ExpenseList();
        Expense expenseFood = new Expense("burger", "2021-10-10", "10");
        Expense expenseEntertainment = new Expense("movie", "2021-10-10", "13");
        String foodExpenseName = expenseFood.getName();
        String foodExpenseDate = expenseFood.getDate().toString();
        String foodExpenseAmount = Double.toString(expenseFood.getAmount());
        String entertainmentExpenseName = expenseEntertainment.getName();
        String entertainmentDate = expenseEntertainment.getDate().toString();
        String entertainmentExpenseAmount = Double.toString(expenseEntertainment.getAmount());
        expenseList.addExpense(foodExpenseName, foodExpenseDate, foodExpenseAmount);
        expenseList.addExpense(entertainmentExpenseName, entertainmentDate, entertainmentExpenseAmount);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        expenseList.viewExpense();
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator()
                + Ui.INDENT + "burger | 2021-10-10 | $10.00" + System.lineSeparator()
                + Ui.INDENT + "movie | 2021-10-10 | $13.00" + System.lineSeparator();
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