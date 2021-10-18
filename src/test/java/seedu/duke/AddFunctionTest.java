package seedu.duke;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddFunctionTest {

    public static final String LIST_OF_EXPENSES = "Here is the list of your expenses:";

    @Test
    public void addExpense_oneAddition_expectSuccess() throws MintException {
        LocalDate date = LocalDate.now();
        ExpenseList expenseList = new ExpenseList();
        Expense expense = new Expense("burger", "2021-10-10", "10");
        String expenseName = expense.getName();
        String expenseDate = expense.getDate().toString();
        String expenseAmount = Double.toString(expense.getAmount());
        String expenseCatNum = "0";
        expenseList.addExpense(expenseName, expenseDate, expenseAmount,expenseCatNum);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        String[] emptyArray = {"view"};
        expenseList.viewExpense(emptyArray);
        String expectedOutput  = LIST_OF_EXPENSES + System.lineSeparator()
                + "      Food      | 2021-10-10 | burger | $10.00" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void addExpense_twoAdditions_expectSuccess() throws MintException {
        LocalDate date = LocalDate.now();
        ExpenseList expenseList = new ExpenseList();
        CategoryList.initialiseCategories();
        Expense expenseFood = new Expense("burger", "2021-10-10", "10", "1");
        Expense expenseEntertainment = new Expense("movie", "2021-10-10", "13", "2");
        String foodExpenseName = expenseFood.getName();
        String foodExpenseDate = expenseFood.getDate().toString();
        String foodExpenseAmount = Double.toString(expenseFood.getAmount());
        String entExpenseName = expenseEntertainment.getName();
        String entDate = expenseEntertainment.getDate().toString();
        String entExpenseAmount = Double.toString(expenseEntertainment.getAmount());
        String foodCatNum = "0";
        String entCatNum = "1";
        expenseList.addExpense(foodExpenseName, foodExpenseDate, foodExpenseAmount, foodCatNum);
        expenseList.addExpense(entExpenseName, entDate, entExpenseAmount, entCatNum);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        String[] emptyArray = {"view"};
        expenseList.viewExpense(emptyArray);
        String expectedOutput  = LIST_OF_EXPENSES + System.lineSeparator()
                + "      Food      | 2021-10-10 | burger | $10.00" + System.lineSeparator()
                + " Entertainment  | 2021-10-10 | movie | $13.00" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void addExpense_wrongAmountFormat_expectErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        parser.executeCommand("add n/burger d/2021-12-23 a/ABCD c/1", expenseList);
        String expectedOutput  = "Please enter a valid amount!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }


    @Test
    public void addExpense_wrongDateFormat_expectErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        parser.executeCommand("add n/movie d/ABCD a/10 c/3", expenseList);
        String expectedOutput  = "Please enter a valid date!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());


    }

    @Test
    public void addExpense_noName_expectErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        Parser parser = new Parser();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        parser.executeCommand("add n/ d/2021-01-01 a/10 c/3", expenseList);
        String expectedOutput = "Please add the description of the item!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}