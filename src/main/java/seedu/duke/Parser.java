package seedu.duke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // take note of the blank space,example " n/"
    public static final String userTag = "\\s[a-z]/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final String STRING_INCLUDE = "Please include the following in your command: \n";
    public static final String STRING_DESCRIPTION = "Description of item\n";
    public static final String STRING_DATE = "Date of purchase\n";
    public static final String STRING_AMOUNT = "Amount of purchase\n";
    public static final String STRING_CATNUM = "Category number of item\n"; 
    public static final String STRING_EMPTY = "";
    public static final String SEPARATOR = ". ";
    protected static final String ERROR_INVALID_AMOUNT = "Please enter a valid amount!";
    protected static final String ERROR_INVALID_DATE = "Please enter a valid date!";
    protected static final String ERROR_INVALID_CATNUM = "Please enter a valid category number!";
    public static final String CAT_NUM_OTHERS = "0";
    protected String command;
    protected String name;
    protected String date;
    protected String amount;
    protected String catNum;
    protected String[] argumentsArray;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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

    private int getCurrentTagIndex(String userInput) {
        int currentTagIndex;
        currentTagIndex = indexOfTag(userInput, userTag);
        return currentTagIndex;
    }

    private String getTagType(String userInput, int currentTagIndex) {
        String tagType;
        tagType = String.valueOf(userInput.charAt(currentTagIndex + 1));
        return tagType;
    }

    private String getDescription(String userInput, int currentTagIndex) {
        String description;
        description = userInput.substring(currentTagIndex + 3).trim();
        return description;
    }

    private String getDescription(String userInput, int currentTagIndex, int nextTagIndex) {
        String description;
        description = userInput.substring(currentTagIndex + 3, nextTagIndex).trim();
        return description;
    }

    private int getNextTagIndex(String userInput, int currentTagIndex) {
        int nextTagIndex;
        nextTagIndex = indexOfTag(userInput.substring(currentTagIndex + 3), userTag) + 3 + currentTagIndex;
        return nextTagIndex;
    }

    private boolean hasNextTag(String userString, int currentTagIndex) {
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
            Integer.parseInt(catNum);
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

    public void parseInputByTags(String userInput) throws MintException {
        // for Add, initialise Date to today's date and category to "Others"
        if (command.equals("add")) {
            initDate();
            initCatNum();
        }

        checkMissingFieldOfUserInput(userInput);
        parseInputByTagsLoop(userInput);
    }


    public void parseInputByArguments(String userInput) {
        argumentsArray = userInput.split(" ");
    }

    public int executeCommand(String userInput, ExpenseList expenseList) throws MintException {
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
                Ui.printCategories();
                break;
            case "list":
                //fallthrough
            case "view":
                parseInputByArguments(userInput);
                expenseList.viewExpense();
                break;
            case "add":
                parseInputByTags(userInput);
                assert name != null : "Name should not be empty";
                assert amount != null : "Amount should not be empty";
                checkValidityOfFields();
                expenseList.addExpense(name, date, amount, catNum);
                break;
            case "delete":
                parseInputByTags(userInput);
                checkValidityOfFields();
                assert !name.equals("") : "Name should not be empty";
                expenseList.deleteExpense(name, date, amount, catNum);
                break;
            case "edit":
                parseInputByTags(userInput);
                checkValidityOfFields();
                expenseList.editExpense(name, date, amount, catNum);
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


    private void checkMissingFieldOfUserInput(String userInput) throws MintException {
        String[] keyDelimiters = command.equals("add")
                ? new String[]{"n/", "a/"}
                : new String[]{"n/", "d/", "a/", "c/"};
        ArrayList<String> missingDelimiters = identifyDelimiters(userInput, keyDelimiters);
        if (missingDelimiters.size() > 0) {
            constructErrorMessage(missingDelimiters);
            throw new MintException(constructErrorMessage(missingDelimiters).toString());
        }
    }

    private ArrayList<String> identifyDelimiters(String userInput, String[] keyDelimiters) {
        ArrayList<String> missingDelimiters = new ArrayList<>();
        for (String delimiter : keyDelimiters) {
            if (!userInput.contains(delimiter)) {
                missingDelimiters.add(delimiter);
            }
        }
        return missingDelimiters;
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
