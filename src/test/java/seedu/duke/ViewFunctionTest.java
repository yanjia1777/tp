package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.ViewCommand;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Interval;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.parser.ViewOptions;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

class ViewFunctionTest {

    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
    RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    BudgetManager budgetManager = new BudgetManager();
    NormalListDataManager normalListDataManager = new NormalListDataManager();
    DataManagerActions dataManagerActions = new DataManagerActions();
    RecurringListDataManager recurringListDataManager = new RecurringListDataManager();
    BudgetDataManager budgetDataManager = new BudgetDataManager();
    Ui ui = new Ui();

    @Test
    void viewWithNormalEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:" + System.lineSeparator()
                    + "  Type  | Category |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Expense |   FOOD   | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "Income  |   GIFT   | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "                                      Net Total: | $249,992.50" + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:" + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,346.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewExpense_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.onlyExpense = true;
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      | Amount | Every |   Until"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50  |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "                                           Net Total: |-$153.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60 | YEAR  | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewIncome_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.onlyIncome = true;
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |  Category  |    Date    |   Name   |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Income  | COMMISSION | 2021-10-09 | OnlyFans | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  | COMMISSION | 2021-09-09 | OnlyFans | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  | COMMISSION | 2021-08-09 | OnlyFans | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  | COMMISSION | 2021-07-09 | OnlyFans | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  | COMMISSION | 2021-06-09 | OnlyFans | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |    GIFT    | 2015-12-15 | Lottery  | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "                                  Net Total: | $251,500.00"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Income  | COMMISSION | 2021-06-09 | OnlyFans | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewByAmountWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.sortType = "amount";
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,346.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewByDateAscendingWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.sortType = "date";
            viewOptions.isAscending = true;
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,346.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewByNameWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.sortType = "name";
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,346.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewByCategoryWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.sortType = "category";
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,346.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewMonthWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.month = Month.JUNE;
            viewOptions.year = 2021;
            viewOptions.isViewAll = false;
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "For the year 2021:"
                    + System.lineSeparator()
                    + "For the month of JUNE:"
                    + System.lineSeparator()
                    + "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |   Name    | Amount  | Every |   Until"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 | OnlyFans  | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 | Maid Cafe |-$14.60  | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "                                      Net Total: | $285.40"
                    + System.lineSeparator()
                    + "Here is the list of recurring entries added to the above list:"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 | OnlyFans  | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 | Maid Cafe |-$14.60  | YEAR  | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewYearWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.year = 2021;
            viewOptions.isViewAll = false;
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "For the year 2021:"
                    + System.lineSeparator()
                    + "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      | Amount  | Every |   Until"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60  | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50   |       |"
                    + System.lineSeparator()
                    + "                                           Net Total: | $1,477.90"
                    + System.lineSeparator()
                    + "Here is the list of recurring entries added to the above list:"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00 | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60  | YEAR  | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewFromWithoutEndDateWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.fromDate = LocalDate.parse("2014-01-01");
            viewOptions.endDate = LocalDate.parse("2021-11-06");
            viewOptions.isViewAll = false;
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "Since 2014-01-01 to 2021-11-06:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,375.70"
                    + System.lineSeparator()
                    + "Here is the list of recurring entries added to the above list:"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewFromWithEndDateWithRecurringEntries_allFieldsValid_success() {

        Expense expense = expense();
        Income income = income();
        RecurringExpense expenseR = recurringExpense();
        RecurringIncome incomeR = recurringIncome();

        try {
            normalFinanceManager.addEntry(expense);
            normalFinanceManager.addEntry(income);
            recurringFinanceManager.addEntry(expenseR);
            recurringFinanceManager.addEntry(incomeR);
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            viewOptions.fromDate = LocalDate.parse("2014-01-01");
            viewOptions.endDate = LocalDate.parse("2019-01-01");
            viewOptions.isViewAll = false;
            ViewCommand command = new ViewCommand(viewOptions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            command.execute(normalFinanceManager, recurringFinanceManager, budgetManager, normalListDataManager,
                    dataManagerActions, recurringListDataManager, budgetDataManager, ui);
            String expectedOutput = "Here is the list of your entries:"
                    + System.lineSeparator()
                    + "Since 2014-01-01 to 2019-01-01:"
                    + System.lineSeparator()
                    + "  Type  |   Category    |    Date    |   Name    |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 | Maid Cafe |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 | Maid Cafe |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 | Maid Cafe |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |  Lottery  | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 | Maid Cafe |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 | Maid Cafe |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator()
                    + "                                      Net Total: | $249,927.00"
                    + System.lineSeparator()
                    + "Here is the list of recurring entries added to the above list:"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 | Maid Cafe |-$14.60      | YEAR  | 2021-11-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    public Expense expense() {
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory categoryE = ExpenseCategory.FOOD;
        return new Expense(name, date, amount, categoryE);
    }

    public RecurringExpense recurringExpense() {
        String name = "Maid Cafe";
        LocalDate date = LocalDate.parse("2012-06-06", dateFormatter);
        Interval interval = Interval.YEAR;
        LocalDate endDate = LocalDate.parse("2021-11-06", dateFormatter);
        double amount = Double.parseDouble("14.6");
        ExpenseCategory categoryE = ExpenseCategory.ENTERTAINMENT;
        return new RecurringExpense(name, date, amount, categoryE, interval, endDate);
    }

    public Income income() {
        String name = "Lottery";
        LocalDate date = LocalDate.parse("2015-12-15", dateFormatter);
        double amount = Double.parseDouble("250000");
        IncomeCategory categoryI = IncomeCategory.GIFT;
        return new Income(name, date, amount, categoryI);
    }

    public RecurringIncome recurringIncome() {
        String name = "OnlyFans";
        LocalDate date = LocalDate.parse("2021-06-09", dateFormatter);
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-11-06", dateFormatter);
        double amount = Double.parseDouble("300");
        IncomeCategory categoryI = IncomeCategory.COMMISSION;
        return new RecurringIncome(name, date, amount, categoryI, interval, endDate);
    }

}