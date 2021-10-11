package seedu.duke;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // take note of the blank space,example " n/"
    public static final String userTag = "\\s[a-z]/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    protected String command;
    protected String name;
    protected String date;
    protected String amount;
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

    public void parseInputByTags(String userInput) throws MintException {
        this.name = null;
        this.date = null;
        this.amount = null;
        String description;
        String tagType;
        boolean hasNext;

        checkValidityOfUserInput(userInput);

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
            default:
                Ui.printInvalidTagError();
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
        switch (command) {
        case "view":
            parseInputByArguments(userInput);
            expenseList.viewExpense();
            break;
        case "add":
            parseInputByTags(userInput);
            expenseList.addExpense(name, date, amount);
            break;
        case "delete":
            parseInputByTags(userInput);
            expenseList.deleteExpense(name, date, amount);
            break;
        case "exit":
            Ui.shutdown();
            return -1;
        default:
            throw new MintException(MintException.ERROR_INVALID_COMMAND);
        }
        return 0;
    }

    private void checkValidityOfUserInput(String userInput) throws MintException {
        String[] keyDelimiters = {"n/", "d/", "a/"};
        for (String delimiter : keyDelimiters) {
            if (!userInput.contains(delimiter)) {
                throw new MintException(MintException.ERROR_NO_DELIMETER);
            }
        }
    }
}
