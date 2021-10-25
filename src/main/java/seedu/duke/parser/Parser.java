package seedu.duke.parser;


import seedu.duke.commands.AddCommand;
import seedu.duke.commands.AddRecurringCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.commands.DeleteRecurringCommand;
import seedu.duke.commands.EditCommand;
import seedu.duke.commands.EditRecurringCommand;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.HelpCommand;
import seedu.duke.commands.InvalidCommand;
import seedu.duke.commands.SetBudgetCommand;
import seedu.duke.commands.ViewBudgetCommand;
import seedu.duke.commands.ViewCommand;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Interval;
import seedu.duke.entries.Type;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.Income;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.exception.MintException;
import seedu.duke.finances.RecurringFinanceManager;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // take note of the blank space,example " n/"
    public static final String userTag = "\\s[a-z]/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final String STRING_EMPTY = "";
    public static final String SEPARATOR = ". ";
    public static final String STRING_INCLUDE = "Please include the following in your command or make them valid: \n";
    public static final String STRING_DESCRIPTION = "Description of item\n";
    public static final String STRING_DATE = "Date of purchase\n";
    public static final String STRING_AMOUNT = "Amount of purchase\n";
    public static final String STRING_CATNUM = "Category number of item\n";
    public static final String STRING_INTERVAL = "Interval of item\n";
    public static final String STRING_END_DATE = "Interval of item\n";
    public static final int CAT_NUM_FOOD_INT = 0;
    public static final int CAT_NUM_OTHERS_INT = 7;
    public static final String CAT_NUM_FOOD = "0";
    public static final String CAT_NUM_OTHERS = "7";
    public static final String ADD_ENTRY = "add";
    public static final String ADD_RECURRING = "addR";
    public static final String DELETE_ENTRY = "delete";
    public static final String DELETE_RECURRING = "deleteR";
    public static final String EDIT_ENTRY = "edit";
    public static final String VIEW = "view";
    public static final String EDIT_RECURRING = "editR";
    public static final String SET_BUDGET = "set";
    public static final String VIEW_BUDGET = "budget";
    public static final String HELP = "help";
    public static final String EXIT = "exit";
    protected String command;
    protected String name;
    protected String dateStr;
    protected LocalDate date;
    protected String amountStr;
    protected double amount;
    protected String catNumStr;
    protected ExpenseCategory expenseCategory;
    protected IncomeCategory incomeCategory;
    protected String intervalStr;
    protected Interval interval;
    protected String endDateStr;
    protected LocalDate endDate;
    protected Type type;
    protected RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    protected boolean isRecurring = false;
    protected String[] argumentsArray;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");

    public Parser() {
    }

    private static int indexOfTag(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.start() : -1;
    }

    public String parserExtractCommand(String userInput) {
        return userInput.split(" ", 2)[0];
    }

    public int getCurrentTagIndex(String userInput) {
        int currentTagIndex;
        currentTagIndex = indexOfTag(userInput, userTag);
        return currentTagIndex;
    }

    public String getTagType(String userInput, int currentTagIndex) {
        String tagType;
        tagType = String.valueOf(userInput.charAt(currentTagIndex + 1));
        return tagType;
    }

    public String getDescription(String userInput, int currentTagIndex) {
        String description;
        description = userInput.substring(currentTagIndex + 3).trim();
        return description;
    }

    public String getDescription(String userInput, int currentTagIndex, int nextTagIndex) {
        String description;
        description = userInput.substring(currentTagIndex + 3, nextTagIndex).trim();
        return description;
    }

    public int getNextTagIndex(String userInput, int currentTagIndex) {
        int nextTagIndex;
        nextTagIndex = indexOfTag(userInput.substring(currentTagIndex + 3), userTag) + 3 + currentTagIndex;
        return nextTagIndex;
    }

    public boolean hasNextTag(String userString, int currentTagIndex) {
        return userString.substring(currentTagIndex + 3).matches(userTagRaw);
    }

    private void setAmountFromTag(String param) throws MintException {
        amountStr = param.trim().substring(2);
        ValidityChecker.checkInvalidAmount(this);
        ValidityChecker.checkPositiveAmount(this);
        amount = Double.parseDouble(amountStr);
    }

    private ExpenseCategory setExpenseCategoryViaCatNum(String catNum) throws MintException {
        switch (catNum) {
        case "0":
            return ExpenseCategory.FOOD;
        case "1":
            return ExpenseCategory.ENTERTAINMENT;
        case "2":
            return ExpenseCategory.TRANSPORTATION;
        case "3":
            return ExpenseCategory.HOUSEHOLD;
        case "4":
            return ExpenseCategory.APPAREL;
        case "5":
            return ExpenseCategory.BEAUTY;
        case "6":
            return ExpenseCategory.GIFT;
        case "7":
            return ExpenseCategory.OTHERS;
        default:
            throw new MintException("Invalid category");
        }
    }

    private IncomeCategory setIncomeCategoryViaCatNum(String catNum) throws MintException {
        switch (catNum) {
        case "0":
            return IncomeCategory.FOOD;
        case "1":
            return IncomeCategory.ENTERTAINMENT;
        case "2":
            return IncomeCategory.TRANSPORTATION;
        case "3":
            return IncomeCategory.HOUSEHOLD;
        case "4":
            return IncomeCategory.APPAREL;
        case "5":
            return IncomeCategory.BEAUTY;
        case "6":
            return IncomeCategory.GIFT;
        case "7":
            return IncomeCategory.OTHERS;
        default:
            throw new MintException("Invalid category");
        }
    }

    private Interval setIntervalViaString(String interval) throws MintException {
        switch (interval) {
        case "MONTHLY":
            return Interval.MONTH;
        case "YEARLY":
            return Interval.YEAR;
        default:
            throw new MintException("Invalid interval");
        }
    }

    private void setFieldsByTag(String description, String tagType) throws MintException {
        switch (tagType) {
        case "n":
            this.name = description;
            break;
        case "d":
            this.dateStr = description;
            break;
        case "a":
            this.amountStr = description;
            break;
        case "c":
            this.catNumStr = description;
            this.expenseCategory = setExpenseCategoryViaCatNum(catNumStr);
            this.incomeCategory = setIncomeCategoryViaCatNum(catNumStr);
            break;
        case "i":
            if (isRecurring) {
                this.intervalStr = description;
            } else {
                throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
            }
            break;
        case "e":
            if (isRecurring) {
                this.endDateStr = description;
            } else {
                throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
            }
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
        }
    }

    private void initDate() {
        this.dateStr = LocalDate.now().toString();
    }

    private void initCatNum() {
        this.catNumStr = CAT_NUM_OTHERS;
    }

    private void initEndDate() {
        this.endDateStr = "2200-12-31";
    }

    private void parseInputByTagsLoop(String userInput) throws MintException {
        String tagType;
        String description;
        while (userInput.matches(userTagRaw)) {
            int currentTagIndex = getCurrentTagIndex(userInput);
            int nextTagIndex = userInput.length();
            tagType = getTagType(userInput, currentTagIndex);

            if (hasNextTag(userInput, currentTagIndex)) {
                nextTagIndex = getNextTagIndex(userInput, currentTagIndex);
                description = getDescription(userInput, currentTagIndex, nextTagIndex);
            } else {
                description = getDescription(userInput, currentTagIndex);
            }

            setFieldsByTag(description, tagType);
            userInput = userInput.substring(nextTagIndex);
        }
    }

    public ArrayList<String> parseInputByTags(String userInput) throws MintException {
        // for Add, initialise Date to today's date and category to "Others"
        try {
            if (command.equals("add")) {
                initDate();
                initCatNum();
            } else if (command.equals("addR")) {
                initDate();
                initCatNum();
                initEndDate();
            }
            parseType(userInput);
            parseInputByTagsLoop(userInput);
            ArrayList<String> validTags = ValidityChecker.checkExistenceAndValidityOfTags(this, userInput);
            isRecurring = false;
            return validTags;
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public void parseType(String userInput) {
        parseInputByArguments(userInput);
        if (Objects.equals(argumentsArray[1], "income")) {
            type = Type.Income;
        } else {
            type = Type.Expense;
        }
    }

    private String[] getParamsWithoutCommand(String[] userInput) {
        return Arrays.copyOfRange(userInput, 1, userInput.length);
    }

    private void setCategoryFromTag(String param) throws MintException {
        catNumStr = param.trim().substring(2);
        ValidityChecker.checkInvalidCatNum(this);
        int catNum = Integer.parseInt(catNumStr);
        expenseCategory = ExpenseCategory.values()[catNum];
    }

    public Entry checkType() {
        if (Objects.equals(argumentsArray[1], "income")) {
            return createIncomeObject();
        } else {
            return createExpenseObject();
        }
    }

    public void parseInputByArguments(String userInput) {
        argumentsArray = userInput.split(" ");
    }


    private Expense createExpenseObject() {
        date = LocalDate.parse(dateStr);
        amount = Double.parseDouble(amountStr);
        int catNum = Integer.parseInt(catNumStr);
        expenseCategory = ExpenseCategory.values()[catNum];
        return new Expense(name, date, amount, expenseCategory);
    }

    private Income createIncomeObject() {
        date = LocalDate.parse(dateStr);
        amount = Double.parseDouble(amountStr);
        int catNum = Integer.parseInt(catNumStr);
        incomeCategory = IncomeCategory.values()[catNum];
        return new Income(name, date, amount, incomeCategory);
    }

    private RecurringExpense createRecurringExpenseObject() throws MintException {
        try {
            date = LocalDate.parse(dateStr);
            amount = Double.parseDouble(amountStr);
            int catNum = Integer.parseInt(catNumStr);
            expenseCategory = ExpenseCategory.values()[catNum];
            interval = Interval.determineInterval(intervalStr);
            endDate = LocalDate.parse(endDateStr);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return new RecurringExpense(name, date, amount, expenseCategory, interval, endDate);
    }

    private RecurringIncome createRecurringIncomeObject() throws MintException {
        try {
            date = LocalDate.parse(dateStr);
            amount = Double.parseDouble(amountStr);
            int catNum = Integer.parseInt(catNumStr);
            expenseCategory = ExpenseCategory.values()[catNum];
            interval = Interval.determineInterval(intervalStr);
            endDate = LocalDate.parse(endDateStr);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return new RecurringIncome(name, date, amount, incomeCategory, interval, endDate);
    }

    private Entry createEntryObject() {
        date = LocalDate.parse(dateStr);
        amount = Double.parseDouble(amountStr);
        return new Entry(name, date, amount);
    }

    private Command prepareAddEntry(String userInput) {
        try {
            parseInputByTags(userInput);
            Entry entry = (type == Type.Income) ? createIncomeObject() : createExpenseObject();
            return new AddCommand(entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareDeleteEntry(String userInput) {
        try {
            ArrayList<String> validTags = parseInputByTags(userInput);
            Entry expense = (type == Type.Income) ? createIncomeObject() : createExpenseObject();
            assert validTags.size() >= 1 : "There should be at least one valid tag";
            return new DeleteCommand(validTags, expense);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareView(String userInput) {
        parseInputByArguments(userInput);
        return new ViewCommand(argumentsArray);
    }

    private Command prepareEditEntry(String userInput) {
        try {
            ArrayList<String> validTags = parseInputByTags(userInput);
            Entry entry = (type == Type.Income) ? createIncomeObject() : createExpenseObject();
            assert validTags.size() >= 1 : "There should be at least one valid tag";
            return new EditCommand(validTags, entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareAddRecurringEntry(String userInput) {
        try {
            isRecurring = true;
            parseInputByTags(userInput);
            RecurringEntry entry = (type == Type.Income)
                    ? createRecurringIncomeObject() : createRecurringExpenseObject();
            return new AddRecurringCommand(entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareDeleteRecurringEntry(String userInput) {
        try {
            isRecurring = true;
            ArrayList<String> validTags = parseInputByTags(userInput);
            RecurringEntry entry = (type == Type.Income)
                    ? createRecurringIncomeObject() : createRecurringExpenseObject();
            return new DeleteRecurringCommand(validTags, entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareEditRecurringEntry(String userInput) {
        try {
            isRecurring = true;
            ArrayList<String> validTags = parseInputByTags(userInput);
            assert validTags.size() >= 1 : "There should be at least one valid tag";
            RecurringEntry entry = (type == Type.Income)
                    ? createRecurringIncomeObject() : createRecurringExpenseObject();
            return new EditRecurringCommand(validTags, entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareSetBudget(String userInput) {
        try {
            parseInputByArguments(userInput);
            String[] params = getParamsWithoutCommand(argumentsArray);
            ValidityChecker.checkSetFormat(params);
            for (String param : params) {
                if (param.startsWith("c/")) {
                    setCategoryFromTag(param);
                } else {
                    setAmountFromTag(param);
                }
            }
            return new SetBudgetCommand(expenseCategory, amount);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    public Command parseCommand(String userInput) {
        this.command = parserExtractCommand(userInput);
        switch (command) {
        case ADD_ENTRY:
            return prepareAddEntry(userInput);
        case DELETE_ENTRY:
            return prepareDeleteEntry(userInput);
        case EDIT_ENTRY:
            return prepareEditEntry(userInput);
        case ADD_RECURRING:
            return prepareAddRecurringEntry(userInput);
        case DELETE_RECURRING:
            return prepareDeleteRecurringEntry(userInput);
        case EDIT_RECURRING:
            return prepareEditRecurringEntry(userInput);
        case SET_BUDGET:
            return prepareSetBudget(userInput);
        case VIEW_BUDGET:
            return new ViewBudgetCommand();
        case VIEW:
            return prepareView(userInput);
        case HELP:
            return new HelpCommand();
        case EXIT:
            return new ExitCommand();
        default:
            return new InvalidCommand(MintException.ERROR_INVALID_COMMAND);
        }
    }
    /*
    case "deleteall":
        deleteCommand = new DeleteCommand();
        deleteCommand.deleteAll(entryList);
        break;
    default:
        isRecurring = false;
        throw new MintException(MintException.ERROR_INVALID_COMMAND);

     */
}
