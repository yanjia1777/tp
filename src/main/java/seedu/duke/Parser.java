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

    public void parseInput(String userInput) {
        this.name = null;
        this.date = null;
        this.amount = null;

        userInput = userInput.trim(); //get rid of whitespaces
        String[] parseCommand = userInput.split(" ", 2); //extract command
        this.command = parseCommand[0];
        String description;
        String tagType;
        int currentTagIndex;
        int nextTagIndex = userInput.length();
        Boolean hasNext;

        //prep userInput for looping
        userInput = userInput.substring(command.length());
        while (userInput.matches(userTagRaw)) {
            hasNext = false;
            currentTagIndex = indexOf(userInput, userTag);
            tagType = String.valueOf(userInput.charAt(currentTagIndex + 1));
            if (userInput.substring(currentTagIndex + 3).matches(userTagRaw)) {
                hasNext = true;
                nextTagIndex = indexOf(userInput.substring(3), userTag) + 3;
                description = userInput.substring(3, nextTagIndex).trim();
            } else {
                description = userInput.substring(currentTagIndex + 3).trim();
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
            }
            if (hasNext) {userInput = userInput.substring(nextTagIndex);} else break;
        }
    }

    public int executeCommand(ExpenseList expenseList) {
        switch (command) {
        case "view":
            //gimin add here
            break;
        case "add":
            expenseList.addExpense(name, date, amount);
            break;
        case "delete":
            //gimin add here
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
