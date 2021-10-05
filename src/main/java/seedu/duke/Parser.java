package seedu.duke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    protected String command;
    protected String arg1;
    protected String arg2;
    protected  String arg3;

    public Parser(String userInput) {
        parseInput(userInput);
    }

    public void parseInput(String userInput) {
        this.arg1 = null;
        this.arg2 = null;
        this.arg3 = null;
        String temp;
        if (userInput.contains("n/")) {
            this.command = userInput.substring(0, userInput.indexOf("n/") - 1);
            temp = userInput.substring(userInput.indexOf("n/") + 2);
            if (temp.contains("d/")) {
                arg1 = temp.substring(0, temp.indexOf("d/") - 1);
                temp = temp.substring(temp.indexOf("d/") + 2);
                if (temp.contains("a/")) {
                    arg2 = temp.substring(0, temp.indexOf("a/") - 1);
                    arg3 = temp.substring(temp.indexOf("a/") +2);
                } else {
                    arg2 = temp;
                }
            } else {
                arg1 = temp;
            }
        } else {
            this.command = userInput;
        }
    }

    public int executeCommand(ExpenseList expenseList) {
        switch(command){
        case "view":
            //gimin add here
            break;
        case "add":
            expenseList.addExpense(arg1, arg2, arg3);
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
