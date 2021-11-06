package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.BudgetManager;
import seedu.duke.commands.ViewCommand;
import seedu.duke.entries.Entry;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

class ViewFunctionTest {

    @Test
    void viewWithNormalEntries_allFieldsValid_success() {
        ArrayList<Entry> outputArray = new ArrayList<>();
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory categoryE = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, categoryE);
        outputArray.add(expense);

        name = "Lottery";
        date = LocalDate.parse("2015-12-15", dateFormatter);
        amount = Double.parseDouble("250000");
        IncomeCategory categoryI = IncomeCategory.GIFT;
        Income income = new Income(name, date, amount, categoryI);
        outputArray.add(income);

        ArrayList<Entry> recurringOutputArray = new ArrayList<>();
        try {
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            ViewCommand view = new ViewCommand(viewOptions);
            Ui ui = new Ui();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            view.view(outputArray,recurringOutputArray, ui, viewOptions.isViewAll);
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

        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        BudgetManager budgetManager = new BudgetManager();
        NormalListDataManager normalListDataManager = new NormalListDataManager();
        DataManagerActions dataManagerActions = new DataManagerActions();
        RecurringListDataManager recurringListDataManager = new RecurringListDataManager();
        BudgetDataManager budgetDataManager = new BudgetDataManager();
        Ui ui = new Ui();

        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory categoryE = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, categoryE);

        name = "Maid Cafe";
        date = LocalDate.parse("2012-06-06", dateFormatter);
        Interval interval = Interval.YEAR;
        LocalDate endDate = LocalDate.parse("2023-01-06", dateFormatter);
        amount = Double.parseDouble("14.6");
        categoryE = ExpenseCategory.ENTERTAINMENT;
        RecurringExpense expenseR = new RecurringExpense(name, date, amount, categoryE, interval, endDate);

        name = "OnlyFans";
        date = LocalDate.parse("2021-06-09", dateFormatter);
        interval = Interval.MONTH;
        endDate = LocalDate.parse("2027-04-01", dateFormatter);
        amount = Double.parseDouble("300");
        IncomeCategory categoryI = IncomeCategory.COMMISSION;
        RecurringIncome incomeR = new RecurringIncome(name, date, amount, categoryI, interval, endDate);

        name = "Lottery";
        date = LocalDate.parse("2015-12-15", dateFormatter);
        amount = Double.parseDouble("250000");
        categoryI = IncomeCategory.GIFT;
        Income income = new Income(name, date, amount, categoryI);

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
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Income  |     GIFT      | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,346.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewByAmountWithRecurringEntries_allFieldsValid_success() {

        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        BudgetManager budgetManager = new BudgetManager();
        NormalListDataManager normalListDataManager = new NormalListDataManager();
        DataManagerActions dataManagerActions = new DataManagerActions();
        RecurringListDataManager recurringListDataManager = new RecurringListDataManager();
        BudgetDataManager budgetDataManager = new BudgetDataManager();
        Ui ui = new Ui();

        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory categoryE = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, categoryE);

        name = "Maid Cafe";
        date = LocalDate.parse("2012-06-06", dateFormatter);
        Interval interval = Interval.YEAR;
        LocalDate endDate = LocalDate.parse("2023-01-06", dateFormatter);
        amount = Double.parseDouble("14.6");
        categoryE = ExpenseCategory.ENTERTAINMENT;
        RecurringExpense expenseR = new RecurringExpense(name, date, amount, categoryE, interval, endDate);

        name = "OnlyFans";
        date = LocalDate.parse("2021-06-09", dateFormatter);
        interval = Interval.MONTH;
        endDate = LocalDate.parse("2027-04-01", dateFormatter);
        amount = Double.parseDouble("300");
        IncomeCategory categoryI = IncomeCategory.COMMISSION;
        RecurringIncome incomeR = new RecurringIncome(name, date, amount, categoryI, interval, endDate);

        name = "Lottery";
        date = LocalDate.parse("2015-12-15", dateFormatter);
        amount = Double.parseDouble("250000");
        categoryI = IncomeCategory.GIFT;
        Income income = new Income(name, date, amount, categoryI);

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
                    + "Income  |  COMMISSION   | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator()
                    + "Expense |     FOOD      | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "                                           Net Total: | $251,346.50"
                    + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator()
                    + "Income  |  COMMISSION   | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01"
                    + System.lineSeparator()
                    + "Expense | ENTERTAINMENT | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }
}