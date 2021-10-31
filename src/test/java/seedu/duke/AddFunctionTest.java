//package seedu.duke;
//
//import org.junit.jupiter.api.Test;
//import seedu.duke.budget.BudgetManager;
//import seedu.duke.commands.AddCommand;
//import seedu.duke.commands.ViewCommand;
//import seedu.duke.entries.Entry;
//import seedu.duke.entries.Expense;
//import seedu.duke.entries.ExpenseCategory;
//import seedu.duke.exception.MintException;
//import seedu.duke.finances.NormalFinanceManager;
//import seedu.duke.finances.RecurringFinanceManager;
//import seedu.duke.parser.Parser;
//import seedu.duke.parser.ViewOptions;
//import seedu.duke.storage.BudgetDataManager;
//import seedu.duke.storage.DataManagerActions;
//import seedu.duke.storage.NormalListDataManager;
//import seedu.duke.storage.RecurringListDataManager;
//import seedu.duke.utility.Ui;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.time.LocalDate;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class AddFunctionTest {
//
//    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
//    RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
//    BudgetManager budgetManager = new BudgetManager();
//    NormalListDataManager normalListDataManager = new NormalListDataManager();
//    DataManagerActions dataManagerActions = new DataManagerActions();
//    RecurringListDataManager recurringListDataManager = new RecurringListDataManager();
//    BudgetDataManager budgetDataManager = new BudgetDataManager();
//    Ui ui = new Ui();
//
//    public static final String LIST_OF_EXPENSES = "Here is the list of your expenses:";
//
//    @Test
//    public void addExpense_oneAddition_expectSuccess() throws MintException {
//        String expenseName = "burger";
//        LocalDate expenseDate = LocalDate.parse("2021-10-10");
//        double expenseAmount = Double.parseDouble("10");
//        ExpenseCategory expenseCat = ExpenseCategory.FOOD;
//        Expense expense = new Expense(expenseName, expenseDate, expenseAmount, expenseCat);
//        AddCommand addCommand = new AddCommand(expense);
//        addCommand.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
//                dataManagerActions, recurringListDataManager, budgetDataManager, ui);
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        // After this all System.out.println() statements will come to outContent stream.
//        String[] viewArray = {"view"};
//        ViewOptions viewOptions = new ViewOptions(viewArray);
//        ViewCommand viewCommand = new ViewCommand(viewOptions);
//        viewCommand.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
//                dataManagerActions, recurringListDataManager, budgetDataManager, ui);
//        String expectedOutput = LIST_OF_EXPENSES + System.lineSeparator()
//                + "  Type  |     Category     |    Date    |   Name   |   Amount    | Every |   Until"
//                + System.lineSeparator()
//                + "Expense |      OTHERS      | 2020-01-06 |  Burger  |-$4.20" + System.lineSeparator()
//                + "                                        Net Total: |-$4.20" + System.lineSeparator()
//                + "Here is the list of recurring entries added to the above list:";
//        assertEquals(expectedOutput, outContent.toString());
//    }
//    /**
//    @Test
//    public void addExpense_twoAdditions_expectSuccess() throws MintException {
//        LocalDate date = LocalDate.now();
//        ExpenseList expenseList = new ExpenseList();
//        CategoryList.initialiseCategories();
//        Expense expenseFood = new Expense("burger", "2021-10-10", "10", "1");
//        Expense expenseEntertainment = new Expense("movie", "2021-10-10", "13", "2");
//        String foodExpenseName = expenseFood.getName();
//        String foodExpenseDate = expenseFood.getDate().toString();
//        String foodExpenseAmount = Double.toString(expenseFood.getAmount());
//        String entExpenseName = expenseEntertainment.getName();
//        String entDate = expenseEntertainment.getDate().toString();
//        String entExpenseAmount = Double.toString(expenseEntertainment.getAmount());
//        String foodCatNum = "0";
//        String entCatNum = "1";
//        expenseList.addExpense(foodExpenseName, foodExpenseDate, foodExpenseAmount, foodCatNum);
//        expenseList.addExpense(entExpenseName, entDate, entExpenseAmount, entCatNum);
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        // After this all System.out.println() statements will come to outContent stream.
//        String[] emptyArray = {"view"};
//        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
//        expenseList.viewExpense(emptyArray, recurringExpenseList);
//        String expectedOutput  = LIST_OF_EXPENSES + System.lineSeparator()
//                + "      Food      | 2021-10-10 | burger | $10.00" + System.lineSeparator()
//                + " Entertainment  | 2021-10-10 | movie | $13.00" + System.lineSeparator();
//        assertEquals(expectedOutput, outContent.toString());
//    }
//
//    @Test
//    public void addExpense_wrongAmountFormat_expectErrorMessage() throws MintException {
//        ExpenseList expenseList = new ExpenseList();
//        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
//        Parser parser = new Parser();
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        parser.executeCommand("add n/burger d/2021-12-23 a/ABCD c/1", expenseList, recurringExpenseList);
//        String expectedOutput  = Parser.STRING_INCLUDE + "1. " + Parser.STRING_AMOUNT + System.lineSeparator();
//        assertEquals(expectedOutput, outContent.toString());
//    }
//
//
//    @Test
//    public void addExpense_wrongDateFormat_expectErrorMessage() throws MintException {
//        ExpenseList expenseList = new ExpenseList();
//        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
//        Parser parser = new Parser();
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        parser.executeCommand("add n/movie d/ABCD a/10 c/3", expenseList, recurringExpenseList);
//        String expectedOutput  = Parser.STRING_INCLUDE + "1. " + Parser.STRING_DATE + System.lineSeparator();
//        assertEquals(expectedOutput, outContent.toString());
//
//
//    }
//
//    @Test
//    public void addExpense_noName_expectErrorMessage() throws MintException {
//        ExpenseList expenseList = new ExpenseList();
//        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
//        Parser parser = new Parser();
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        parser.executeCommand("add n/ d/2021-01-01 a/10 c/3", expenseList, recurringExpenseList);
//        String expectedOutput = Parser.STRING_INCLUDE + "1. " + Parser.STRING_DESCRIPTION + System.lineSeparator();
//        assertEquals(expectedOutput, outContent.toString());
//    }
//    **/
//}