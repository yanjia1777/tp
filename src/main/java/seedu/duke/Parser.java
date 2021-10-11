package seedu.duke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;

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
    protected String command;
    protected String name;
    protected String date;
    protected String amount;
    protected String catNum;
    protected String[] argumentsArray;

    public Parser() {
    }

    public static int indexOf(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.start() : -1;
    }

    public String parserExtractCommand(String userInput) {
        return userInput.split(" ", 2)[0];
    }

    private int getCurrentTagIndex(String userInput) {
        int currentTagIndex;
        currentTagIndex = indexOf(userInput, userTag);
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
        nextTagIndex = indexOf(userInput.substring(currentTagIndex + 3), userTag) + 3 + currentTagIndex;
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
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    private void checkEmptyName() throws MintException {
        boolean hasEmptyName = name.equals(STRING_EMPTY);
        if (hasEmptyName) {
            throw new MintException(MintException.ERROR_NO_NAME);
        }
    }

    private void checkInvalidAmount() throws MintException {
        try {
            Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            throw new MintException(ERROR_INVALID_AMOUNT);
        }
    }

    private void checkInvalidDate() throws MintException {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new MintException(ERROR_INVALID_DATE);
        }
    }


    public void parseInputByTags(String userInput) throws MintException {
        this.name = null;
        this.date = LocalDate.now().toString(); //default
        this.amount = null;
        this.catNum = "0"; //default - Others
        String description;
        String tagType;
        boolean hasNext;

        checkMissingFieldOfUserInput(userInput);

        int nextTagIndex = userInput.length();

        //prep userInput for looping
        userInput = userInput.substring(command.length());
        while (userInput.matches(userTagRaw)) {
            hasNext = false;
            int currentTagIndex = getCurrentTagIndex(userInput);
            tagType = getTagType(userInput, currentTagIndex);
            if (hasNextTag(userInput, currentTagIndex)) {
                hasNext = true;
                nextTagIndex = getNextTagIndex(userInput, currentTagIndex);
                description = getDescription(userInput, currentTagIndex, nextTagIndex);
            } else {
                description = getDescription(userInput, currentTagIndex);
            }

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

            if (hasNext) {
                userInput = userInput.substring(nextTagIndex);
            } else {
                break;
            }
        }
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
            case "view":
                parseInputByArguments(userInput);
                expenseList.viewExpense();
                break;
            case "add":
                parseInputByTags(userInput);
                checkValidityOfFields();
                expenseList.addExpense(name, date, amount, catNum);
                break;
            case "delete":
                parseInputByTags(userInput);
                checkValidityOfFields();
                expenseList.deleteExpense(name, date, amount);
                break;
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
        String[] keyDelimiters = {"n/", "d/", "a/", "c/"};
        ArrayList<String> missingDelimiters = new ArrayList<>();
        StringBuilder missingFields = new StringBuilder();
        missingFields.append(STRING_INCLUDE);
        int index = 1;
        for (String delimiter : keyDelimiters) {
            if (!userInput.contains(delimiter)) {
                missingDelimiters.add(delimiter);
            }
        }
        if (missingDelimiters.size() > 0) {
            for (String delimiter : missingDelimiters) {
                switch (delimiter) {
                case "n/":
                    missingFields.append(index).append(SEPARATOR).append(STRING_DESCRIPTION);
                    index++;
                    break;
                case "d/":
                    missingFields.append(index).append(SEPARATOR).append(STRING_DATE);
                    index++;
                    break;
                case "a/":
                    missingFields.append(index).append(SEPARATOR).append(STRING_AMOUNT);
                    index++;
                    break;
                case "c/":
                    missingFields.append(index).append(SEPARATOR).append(STRING_CATNUM);
                    index++;
                    break;
                default:
                    throw new MintException(MintException.ERROR_INVALID_COMMAND);
                }
            }
            throw new MintException(missingFields.toString());
        }
    }
}
