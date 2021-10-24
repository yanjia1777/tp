package seedu.duke.parser;

import seedu.duke.Entry;
import seedu.duke.Expense;
import seedu.duke.Income;
import seedu.duke.MintException;
import seedu.duke.RecurringExpenseList;
import seedu.duke.Ui;
import seedu.duke.commands.AddCommand;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.commands.EditCommand;
import seedu.duke.commands.ViewCommand;
import seedu.duke.CategoryList;

import java.io.File;
import java.time.LocalDate;
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
    public static final int CAT_NUM_FOOD_INT = 0;
    public static final int CAT_NUM_OTHERS_INT = 7;
    public static final String CAT_NUM_FOOD = "0";
    public static final String CAT_NUM_OTHERS = "7";
    protected String command;
    protected String name;
    protected String date;
    protected String amount;
    protected String catNum;
    protected String interval;
    protected String endDate;
    protected boolean isRecurring = false;
    protected String[] argumentsArray;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";

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

    private void setAmount(String param) {
        amount = param.trim();
    }

    private void setFieldsByTag(String description, String tagType) throws MintException {
        switch (tagType) {
        case "n":
            this.name = description;
            break;
        case "d":
            this.date = description;
            break;
        case "a":
            this.amount = description;
            break;
        case "c":
            this.catNum = description;
            break;
        case "i":
            if (isRecurring) {
                this.interval = description;
            } else {
                throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
            }
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
        }
    }

    private void initDate() {
        this.date = LocalDate.now().toString();
    }

    private void initCatNum() {
        this.catNum = CAT_NUM_OTHERS;
    }

    private void initEndDate() {
        this.endDate = "2200-12-31";
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
        if (command.equals("add")) {
            initDate();
            initCatNum();
        } else if (command.equals("addR")) {
            initDate();
            initCatNum();
            initEndDate();
        }

        parseInputByTagsLoop(userInput);
        return ValidityChecker.checkExistenceAndValidityOfTags(this, userInput);
    }

    public void parserSetLimit(String[] userInput) throws MintException {
        ValidityChecker.checkSetFormat(userInput);
        String[] parameters = getParamsWithoutCommand(userInput);
        for (String param : parameters) {
            if (param.contains("c/")) {
                extractCatNumFromTag(param);
            } else {
                setAmount(param);
            }
        }
    }

    private String[] getParamsWithoutCommand(String[] userInput) {
        return Arrays.copyOfRange(userInput, 1, userInput.length);
    }

    private void extractCatNumFromTag(String param) {
        catNum = String.valueOf(param.trim().charAt(2));
    }

    public Entry checkType (String[] argumentsArray) {
        if(Objects.equals(argumentsArray[1], "income")) {
            return new Income(name, date, amount, catNum);
        } else {
            return new Expense(name, date, amount, catNum);
        }
    }

    public void parseInputByArguments(String userInput) {
        argumentsArray = userInput.split(" ");
    }

    public int executeCommand(String userInput, ArrayList<Entry> entryList,
                              RecurringExpenseList recurringExpenseList) throws MintException {
        ArrayList<String> validTags;
        userInput = userInput.trim(); //get rid of whitespaces
        this.command = parserExtractCommand(userInput);
        try {
            switch (command) {
            case "help":
                Ui.help();
                break;
            case "cat":
                //fallthrough
            case "category":
                //fallthrough
            case "categories":
                CategoryList.viewCategories();
                break;
            case "list":
                //fallthrough
            case "view":
                parseInputByArguments(userInput);
                //                expenseList.viewExpense(argumentsArray, recurringExpenseList);
                ViewCommand viewCommand = new ViewCommand();
                viewCommand.view(argumentsArray, recurringExpenseList, entryList);
                break;
            case "limit":
                parseInputByArguments(userInput);
                parserSetLimit(argumentsArray);
                ValidityChecker.checkInvalidAmount(this);
                ValidityChecker.checkInvalidCatNum(this);
                CategoryList.setLimit(catNum, amount);
                Ui.setLimitMessage(catNum, amount);
                break;
            case "spending":
                //fallthrough
            case "breakdown":
                CategoryList.viewMonthlyLimit();
                break;
            case "add":
                parseInputByArguments(userInput);
                parseInputByTags(userInput);
                assert name != null : "Name should not be empty";
                assert amount != null : "Amount should not be empty";
                AddCommand addCommand = new AddCommand();
                addCommand.add(checkType(argumentsArray), entryList);
                break;
            case "delete":
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                //                expenseList.deleteExpenseByKeywords(validTags, name, date, amount, catNum);
                //                Expense expense = new Expense(name, date, amount, catNum);
                Entry entry = new Entry(name, date, amount, catNum);
                DeleteCommand deleteCommand = new DeleteCommand();
                deleteCommand.deleteByKeywords(validTags, entry, entryList);
                break;
            case "edit":
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                //                expenseList.editExpenseByKeywords(validTags, expense, expenseList);
                EditCommand editCommand = new EditCommand();
                entry = new Entry(name, date, amount, catNum);
                editCommand.editByKeywords(validTags, entry, entryList);
                break;
            case "addR":
                isRecurring = true;
                parseInputByTags(userInput);
                assert name != null : "Name should not be empty";
                assert amount != null : "Amount should not be empty";
                recurringExpenseList.addRecurringExpense(name, date, amount, catNum, interval, endDate);
                break;
            case "deleteR":
                isRecurring = true;
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                recurringExpenseList.deleteRecurringExpenseByKeywords(validTags, name, date, amount, catNum, interval);
                break;
            case "editR":
                isRecurring = true;
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                recurringExpenseList.editRecurringExpenseByKeywords(validTags, name, date, amount, catNum, interval);
                break;
            case "bye":
                //fallthrough
            case "exit":
                Ui.shutdown();
                return -1;
            default:
                isRecurring = false;
                throw new MintException(MintException.ERROR_INVALID_COMMAND);
            }
        } catch (MintException e) {
            isRecurring = false;
            System.out.println(e.getMessage());
        }
        isRecurring = false;
        return 0;
    }

}
