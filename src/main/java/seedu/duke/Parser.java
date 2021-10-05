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

    public Parser(String userInput) {
        parseInput(userInput);
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


    private int getNextTagIndex(String userInput) {
        int nextTagIndex;
        nextTagIndex = indexOf(userInput.substring(3), userTag) + 3;
        return nextTagIndex;
    }

    private boolean hasNextTag(String userString, int currentTagIndex) {
        return userString.substring(currentTagIndex + 3).matches(userTagRaw);
    }

    public void parseInput(String userInput) {
        this.name = null;
        this.date = null;
        this.amount = null;
        String description;
        String tagType;
        int currentTagIndex;
        int nextTagIndex;
        boolean hasNext;

        userInput = userInput.trim(); //get rid of whitespaces
        this.command = parserExtractCommand(userInput);
        if (userInput.length() == command.length()) return; //short circuit
        nextTagIndex = userInput.length();

        //prep userInput for looping
        userInput = userInput.substring(command.length());
        while (userInput.matches(userTagRaw)) {
            hasNext = false;
            currentTagIndex = getCurrentTagIndex(userInput);
            tagType = getTagType(userInput, currentTagIndex);
            if (hasNextTag(userInput, currentTagIndex)) {
                hasNext = true;
                nextTagIndex = getNextTagIndex(userInput);
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

    public int executeCommand(ExpenseList expenseList) {
        switch (command) {
        case "view":
            expenseList.view();
            break;
        case "add":
            expenseList.addExpense(name, date, amount);
            break;
        case "delete":
            expenseList.deleteExpense(name, date, amount);
            break;
        case "exit":
            Ui.shutdown();
            return -1;
        default:
            Ui.printError();
        }
        return 0;
    }
}
