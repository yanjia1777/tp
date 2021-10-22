package seedu.duke;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // take note of the blank space,example " n/"
    public static final String userTag = "\\s[a-z]/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final String STRING_INCLUDE = "Please include the following in your command or make them valid: \n";
    public static final String STRING_DESCRIPTION = "Description of item\n";
    public static final String STRING_DATE = "Date of purchase\n";
    public static final String STRING_AMOUNT = "Amount of purchase\n";
    public static final String STRING_CATNUM = "Category number of item\n";
    public static final String STRING_EMPTY = "";
    public static final String SEPARATOR = ". ";
    protected static final String ERROR_INVALID_AMOUNT = "Please enter a valid amount!";
    protected static final String ERROR_INVALID_DATE = "Please enter a valid date!";
    protected static final String ERROR_INVALID_CATNUM = "Please enter a valid category number! c/0 to c/7";
    public static final int CAT_NUM_FOOD_INT = 0;
    public static final int CAT_NUM_OTHERS_INT = 7;
    public static final String CAT_NUM_FOOD = "0";
    public static final String CAT_NUM_OTHERS = "7";
    public static final String setFormatErrorMessage = "Please follow format {set c/catNum limit}\n e.g. set c/0 100";
    protected String command;
    protected String name;
    protected String date;
    protected String amount;
    protected String catNum;
    protected ExpenseCategory category;
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

    private void checkValidityOfFields() throws MintException {
        try {
            checkEmptyName();
            checkInvalidAmount();
            checkInvalidDate();
            checkInvalidCatNum();
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    private void checkEmptyName() throws MintException {
        boolean hasEmptyName = name.equals(STRING_EMPTY);
        if (hasEmptyName) {
            logger.log(Level.INFO, "User entered empty name");
            throw new MintException(MintException.ERROR_NO_NAME);
        }
    }

    private void checkInvalidAmount() throws MintException {
        try {
            Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid amount");
            throw new MintException(ERROR_INVALID_AMOUNT);
        }
    }

    private void checkInvalidDate() throws MintException {
        try {
            LocalDate.parse(date, Expense.dateFormatter);
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException(ERROR_INVALID_DATE);
        }
    }

    private void checkInvalidCatNum() throws MintException {
        try {
            int catNumInt = Integer.parseInt(catNum);
            if (catNumInt < CAT_NUM_FOOD_INT || catNumInt > CAT_NUM_OTHERS_INT) {
                throw new MintException(ERROR_INVALID_CATNUM);
            }
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid category number");
            throw new MintException(ERROR_INVALID_CATNUM);
        }
    }

    private void initDate() {
        this.date = LocalDate.now().toString();
    }

    private void initCatNum() {
        this.catNum = CAT_NUM_OTHERS;
    }

    private void initCat() {
        this.category = ExpenseCategory.OTHERS;
    }

    private ExpenseCategory setCategoryViaCatNum(String catNum) throws MintException {
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
            this.category = setCategoryViaCatNum(description);
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
        }
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
            initCat();
        }

        parseInputByTagsLoop(userInput);
        return checkExistenceAndValidityOfTags(userInput);
    }

    public void parserSetLimit(String[] userInput) throws MintException {
        checkSetFormat(userInput);
        String[] parameters = getParamsWithoutCommand(userInput);
        for (String param : parameters) {
            if (param.contains("c/")) {
                extractCatNumFromTag(param);
            } else {
                setAmount(param);
            }
        }
    }

    private void setAmount(String param) {
        amount = param.trim();
    }

    private String[] getParamsWithoutCommand(String[] userInput) {
        return Arrays.copyOfRange(userInput, 1, userInput.length);
    }

    private void checkSetFormat(String[] userInput) throws MintException {
        if (userInput.length != 3) {
            throw new MintException(setFormatErrorMessage);
        }
    }

    private void extractCatNumFromTag(String param) {
        catNum = String.valueOf(param.trim().charAt(2));
    }

    public void parseInputByArguments(String userInput) {
        argumentsArray = userInput.split(" ");
    }

    public int executeCommand(String userInput, ExpenseList expenseList) throws MintException {
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
                expenseList.viewExpense(argumentsArray);
                break;
            case "limit":
                parseInputByArguments(userInput);
                parserSetLimit(argumentsArray);
                checkInvalidAmount();
                checkInvalidCatNum();
                CategoryList.setLimit(catNum, amount);
                Ui.setLimitMessage(catNum, amount);
                break;
            case "spending":
                //fallthrough
            case "breakdown":
                CategoryList.viewMonthlyLimit();
                break;
            case "add":
                parseInputByTags(userInput);
                assert name != null : "Name should not be empty";
                assert amount != null : "Amount should not be empty";
                expenseList.addExpense(name, date, amount, category);
                //expenseList.addExpense(name, date, amount, catNum);
                break;
            case "delete":
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                expenseList.deleteExpenseByKeywords(validTags, name, date, amount, catNum);
                break;
            case "edit":
                validTags = parseInputByTags(userInput);
                expenseList.editExpenseByKeywords(validTags, name, date, amount, catNum);
                break;
            case "bye":
                //fallthrough
            case "exit":
                Ui.shutdown();
                return -1;
            default:
                throw new MintException(MintException.ERROR_INVALID_COMMAND);
            }
        } catch (MintException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private ArrayList<String> identifyValidTags(String userInput, String[] mandatoryTags) throws MintException {
        ArrayList<String> validTags = new ArrayList<>();
        ArrayList<String> invalidTags = new ArrayList<>();
        String[] tags = {"n/", "d/", "a/", "c/"};
        List<String> mandatoryTagsToBeChecked = Arrays.asList(mandatoryTags);

        for (String tag : tags) {
            try {
                if (userInput.contains(tag.trim())) {
                    switch (tag.trim()) {
                    case "n/":
                        checkEmptyName();
                        break;
                    case "d/":
                        checkInvalidDate();
                        break;
                    case "a/":
                        checkInvalidAmount();
                        break;
                    case "c/":
                        checkInvalidCatNum();
                        break;
                    default:
                        throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
                    }
                    validTags.add(tag);
                } else if (mandatoryTagsToBeChecked.contains(tag)) {
                    invalidTags.add(tag);
                }
            } catch (MintException e) {
                invalidTags.add(tag);
            }
        }

        if (invalidTags.size() > 0) {
            constructErrorMessage(invalidTags);
            throw new MintException(constructErrorMessage(invalidTags).toString());
        } else if (validTags.size() == 0) {
            throw new MintException("Please enter at least one tag.");
        }
        return validTags;
    }

    private ArrayList<String> checkExistenceAndValidityOfTags(String userInput) throws MintException {
        try {
            String[] mandatoryTags = command.equals("add")
                    ? new String[]{"n/", "a/"}
                    : new String[]{};
            return identifyValidTags(userInput, mandatoryTags);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    private StringBuilder constructErrorMessage(ArrayList<String> missingDelimiters) throws MintException {
        int index = 1;
        StringBuilder missingFieldsErrorMessage = new StringBuilder();
        missingFieldsErrorMessage.append(STRING_INCLUDE);
        for (String delimiter : missingDelimiters) {
            switch (delimiter) {
            case "n/":
                missingFieldsErrorMessage.append(index).append(SEPARATOR).append(STRING_DESCRIPTION);
                index++;
                break;
            case "d/":
                missingFieldsErrorMessage.append(index).append(SEPARATOR).append(STRING_DATE);
                index++;
                break;
            case "a/":
                missingFieldsErrorMessage.append(index).append(SEPARATOR).append(STRING_AMOUNT);
                index++;
                break;
            case "c/":
                missingFieldsErrorMessage.append(index).append(SEPARATOR).append(STRING_CATNUM);
                index++;
                break;
            default:
                throw new MintException(MintException.ERROR_INVALID_COMMAND);
            }
        }
        return missingFieldsErrorMessage;
    }
}
