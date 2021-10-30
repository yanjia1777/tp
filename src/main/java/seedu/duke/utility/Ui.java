package seedu.duke.utility;

import seedu.duke.budget.Budget;
import seedu.duke.exception.MintException;
import seedu.duke.parser.Parser;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Type;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.Interval;
import seedu.duke.entries.ExpenseCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ui {
    private Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public void printError(MintException e) {
        System.out.println(e.getMessage());
    }

    public boolean hasUnsafeCharacters(String text) {
        Pattern pattern = Pattern.compile("[$&+,:;=\\\\?@#|'<>^*()%!]");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public String readUserInput() {
        return in.nextLine().trim();
    }

    protected static final String INDENT = "    ";
    public static final String SOLID_LINE = "_______________________________________________________________________\n";
    public static final String LINE = "    ____________________________________________________________";
    public static final String SUCCESSFUL_EDIT_MESSAGE = "Got it! I will update the fields accordingly!";
    protected static final String LINE_SEPARATOR = System.lineSeparator();
    protected static final int INDEX_CANCEL = -1;
    protected static final String CANCEL_MESSAGE = " To cancel, type \"cancel\"";
    public static final String MISSING_FILE_MESSAGE = "Missing data detected! Creating the necessary files...";
    public static final String MISSING_FIELDS_MESSAGE = "There seems to be some extra/missing fields! "
            + "Please delete the text files and try again!";

    public void printGreetings() {
        System.out.println("Hello! I'm Mint");
        System.out.println("What can I do for you?");
    }

    public static void shutdown() {
        System.out.println("Goodbye! Hope to see you again soon!");
    }

    public static void printInvalidTagError() {
        System.out.println("Sorry, we are only able to process name, date and process for now");
        System.out.println("The following tags are available: n/ d/ a/");
    }

    public void help() {
        System.out.println("Available tags: n/name d/date a/AMOUNT c/CATEGORY_NUMBER i/interval e/endDate\n"
                + "Order of tags does not matter.\n"
                + "Square brackets \"[ ]\" identifies an optional argument.\n"
                + "List of commands available.\n"
                + SOLID_LINE
                + "KEYING IN ENTRIES\n"
                + "- add n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Add expense. Example: add n/chicken rice a/3.50 d/2021-09-30 c/1\n"
                + "- add income n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Add income. Example: add n/payday a/400 d/2021-10-10 c/1\n"
                + "- delete [n/{keyword}] [a/AMOUNT] [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Delete entries using keyword search. Needs at least 1 tag. Example: delete n/chicken\n"
                + "- edit [n/{keyword}] [a/AMOUNT] [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Edit entries using keyword search. Needs at least 1 tag. Example: edit n/chicken\n"
                + SOLID_LINE
                + "RECURRING EXPENSES AND INCOME\n"
                + "Similar to keying in entries, but includes interval(mandatory for adding), endDate(optional)"
                + "Commands requires a \"R\", \n"
                + "e.g. addR, addR income, deleteR, editR\n\n"
                + "- addR n/NAME a/AMOUNT i/INTERVAL [d/START_DATE] [e/END_DATE] [c/CATEGORY_NUMBER] \n"
                + INDENT + "Add recurring expenses. Example: add n/spotify subscription a/10 i/MONTH d/2021-09-30 c/1\n"
                + "- addR income n/NAME a/AMOUNT i/INTERVAL [d/START_DATE] [e/END_DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Add recurring income. Example: add n/payday a/400 i/MONTH d/2021-10-10 c/1\n"
                + "- deleteR [n/{keyword}] [i/INTERVAL] [d/START_DATE] [e/END_DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Delete recurring entries using keyword search. Needs at least 1 tag. "
                + "Example: deleteR n/spotify\n"
                + "- editR [n/{keyword}] [a/AMOUNT] [d/DATE] [c/category number]\n"
                + INDENT + "Edit recurring entries using keyword search. Needs at least 1 tag. "
                + "Example: editR n/spotify\n"
                + SOLID_LINE
                + "BUDGETING\n"
                + "- set c/category number a/AMOUNT\n"
                + INDENT + "Set spending limit for individual category. Example: set c/0 100\n"
                + "- budget\n"
                + INDENT + "View current month's expenditure and budget\n"
                + SOLID_LINE
                + "UTILITIES\n"
                + "- view\n"
                + INDENT + "View expenses\n"
                + "- cat\n"
                + INDENT + "View categories and category number\n"
                + "- exit\n"
                + INDENT + "Exits the app\n"
                + SOLID_LINE
        );
    }

    public static void viewGivenList(ArrayList<Entry> list) {
        System.out.println("Here is the list of items containing the keyword.");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(INDENT + (i + 1) + "  " + list.get(i).toString());
        }
    }

    public static void viewGivenListAndTotal(ArrayList<Entry> list, double totalAmount) {
        System.out.println("Here is the list of your entries:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(INDENT + (i + 1) + "  " + list.get(i).toString());
        }
        System.out.println(LINE);
        System.out.println(INDENT + "Total: " + totalAmount);
    }

    public static int chooseItemToDeleteOrEdit(ArrayList<Entry> filteredList, boolean isDelete) throws MintException {
        if (isDelete) {
            System.out.println("Enter the index of the item you want to delete." + CANCEL_MESSAGE);
        } else {
            System.out.println("Enter the index of the item you want to edit." + CANCEL_MESSAGE);
        }

        Scanner in = new Scanner(System.in);
        int index = 0;
        boolean proceedToDelete = false;
        while (!proceedToDelete) {
            String userInput = in.nextLine();
            if (userInput.trim().equals("cancel")) {
                return INDEX_CANCEL;
            }
            try {
                index = Integer.parseInt(userInput);
                if (index < 1 || index > filteredList.size()) {
                    System.out.println(MintException.ERROR_INDEX_OUT_OF_BOUND + CANCEL_MESSAGE);
                } else {
                    proceedToDelete = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(MintException.ERROR_INDEX_INVALID_NUMBER + CANCEL_MESSAGE);
            }
        }
        return index - 1;
    }

    public static boolean isConfirmedToDeleteOrEdit(Entry entry, boolean isDelete) {
        if (isDelete) {
            System.out.println("Is this what you want to delete?");
        } else {
            System.out.println("Is this what you want to edit?");
        }
        System.out.println(INDENT + entry);
        System.out.println("Type \"y\" if yes. Type \"n\" if not.");
        return isConfirmed();
    }

    public static boolean isConfirmDeleteAll() {
        System.out.println("Are you sure you want to delete all entries?");
        return isConfirmed();
    }

    public static boolean isConfirmed() {
        Scanner in = new Scanner(System.in);
        while (true) {
            String userInput = in.nextLine();
            switch (userInput.trim()) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                System.out.println("Sorry I don't understand what that means. +"
                        + "Type \"y\" if yes. Type \"n\" if not.");
                break;
            }
        }
    }

    public static void deleteAllConfirmation() {
        System.out.println("All entries successfully deleted.");
    }

    public static void deleteAborted() {
        System.out.println("Delete aborted.");
    }

    public static void printOutcomeOfEditAttempt() {
        System.out.println(SUCCESSFUL_EDIT_MESSAGE);
    }

    public void printCategoryList() {
        System.out.println("Here are the categories and its tag number\n"
                + "Expenses           | Income\n"
                + "c/0 FOOD           | c/0 ALLOWANCE\n"
                + "c/1 ENTERTAINMENT  | c/1 WAGES\n"
                + "c/2 TRANSPORTATION | c/2 SALARY\n"
                + "c/3 HOUSEHOLD      | c/3 INTERESTED\n"
                + "c/4 APPAREL        | c/4 INVESTMENT\n"
                + "c/5 BEAUTY         | c/5 COMMISSION\n"
                + "c/6 GIFT           | c/6 GIFT\n"
                + "c/7 OTHERS         | c/7 OTHERS\n");
    }

    public static void printMissingFileMessage() {
        System.out.println(MISSING_FILE_MESSAGE);
    }

    public static void printFieldsErrorMessage() {
        System.out.println(MISSING_FIELDS_MESSAGE);
    }

    //    public static void setLimitMessage(String catNumString, String amount) {
    //        int catNumInt = Integer.parseInt(catNumString);
    //        System.out.println("Set Limit of " + CategoryList.getCatName(catNumInt) + " to $" + amount);
    //    }

    public static StringBuilder constructErrorMessage(ArrayList<String> missingDelimiters) throws MintException {
        int index = 1;
        StringBuilder missingFieldsErrorMessage = new StringBuilder();
        missingFieldsErrorMessage.append(Parser.STRING_INCLUDE);
        for (String delimiter : missingDelimiters) {
            switch (delimiter) {
            case "n/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_DESCRIPTION);
                index++;
                break;
            case "d/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_DATE);
                index++;
                break;
            case "a/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_AMOUNT);
                index++;
                break;
            case "c/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_CATNUM);
                index++;
                break;
            case "i/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_INTERVAL);
                index++;
                break;
            case "e/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_END_DATE);
                index++;
                break;
            default:
                throw new MintException(MintException.ERROR_INVALID_COMMAND);
            }
        }
        return missingFieldsErrorMessage;
    }

    public int[] printView(ArrayList<Entry> outputArray, LocalDate fromDate, LocalDate endDate, double total) {
        int maxNameLength = 4;
        int maxAmountLength = 5;
        System.out.println("Here is the list of your entries:");
        if (fromDate != null) {
            System.out.println("Since " + fromDate + " to " + endDate + ":");
        }
        for (Entry entry : outputArray) {
            if (entry.getName().length() > maxNameLength) {
                maxNameLength = entry.getName().length();
            }
            if (String.format("%,.2f", entry.getAmount()).length() > maxAmountLength) {
                maxAmountLength = String.format("%,.2f", entry.getAmount()).length();
            }
        }
        System.out.println("  Type  |     Category     |    Date    | " + getNameIndented("Name", maxNameLength)
                + " | " + getNameIndented("Amount", maxAmountLength + 1) + " | Every |   Until");
        for (Entry entry : outputArray) {
            printViewIndividualEntry(entry, maxNameLength, maxAmountLength);
        }
        System.out.print(getIndent(maxNameLength, 0, "")
                + "                                Net Total: |");
        if (total < 0) {
            total = Math.abs(total);
            System.out.print("-$" + String.format("%,.2f", total));
        } else {
            System.out.print(" $" + String.format("%,.2f", total));
        }
        System.out.println();
        return new int[]{maxNameLength, maxAmountLength};
    }

    private void printViewIndividualEntry(Entry entry, int maxNameLength, int maxAmountLength) {
        String type = entry.getType() == Type.Expense ? entry.getType().toString() : entry.getType() + " ";
        StringBuilder category = getCategoryIndented(entry.getCategory());
        String date = entry.getDate().toString();
        String name = getNameIndented(entry.getName(), maxNameLength);
        String amount = getAmountIndented(String.format("%,.2f", entry.getAmount()), maxAmountLength);
        String negativeSign = entry.getType() == Type.Expense ? "-$" : " $";
        if (entry instanceof RecurringEntry) {
            String interval = entry.getInterval() == Interval.MONTH ? entry.getInterval().toString()
                    : entry.getInterval() + " ";
            String until = entry.getEndDate().toString();
            until = until.equals("2200-12-31") ? "Forever :D" : until;
            System.out.println(type + " | " + category + " | " + date + " | " + name + " |" + negativeSign + amount
                    + " | " + interval + " | " + until);
        } else {
            System.out.println(type + " | " + category + " | " + date + " | " + name + " |" + negativeSign + amount);
        }
    }

    public void printViewRecurring(ArrayList<Entry> entryList, int maxNameIndent, int maxAmountIndent) {
        System.out.println("Here is the list of recurring entries added to the above list:");
        for (Entry entry : entryList) {
            printViewIndividualEntry(entry, maxNameIndent, maxAmountIndent);
        }
    }

    public static StringBuilder getIndent(int leftIndent, int rightIndent, String item) {
        StringBuilder itemWithIndent = new StringBuilder();
        while (leftIndent != 0) {
            itemWithIndent.append(" ");
            leftIndent--;
        }

        itemWithIndent.append(item);

        while (rightIndent != 0) {
            itemWithIndent.append(" ");
            rightIndent--;
        }
        return itemWithIndent;
    }

    public static StringBuilder getCategoryIndented(Enum category) {
        double length = category.name().length();
        int leftIndent = (int) Math.floor((16 - length) / 2);
        int rightIndent = (int) Math.ceil((16 - length) / 2);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return getIndent(leftIndent, rightIndent, category.name());
    }

    public static String getAmountIndented(String amount, int indent) {
        double length = amount.length();
        int rightIndent = (int) (indent - length);
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return getIndent(0, rightIndent, amount).toString();
    }

    public static String getNameIndented(String name, int indent) {
        double length = name.length();
        int leftIndent = (int) Math.floor((indent - length) / 2);
        int rightIndent = (int) Math.ceil((indent - length) / 2);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return Ui.getIndent(leftIndent, rightIndent, name).toString();
    }


    public void printEntryAdded(Entry entry) {
        System.out.println("I've added: " + entry);
    }

    public void printInvalidCommand(String message) {
        System.out.println(message);
    }

    public void printEntryDeleted(Entry entry) {
        System.out.println("I have deleted: " + entry);
    }

    public void printSetBudget(ExpenseCategory category, double amount) {
        System.out.printf("Budget for %s set to $%.2f\n", category, amount);
    }

    public void printBudgetBreakdown(ArrayList<Budget> budgetList, ArrayList<Entry> entryList) {
        System.out.println("Here is the budget for the month.");
        for (Budget budget : budgetList) {
            String categoryIndented = getCategoryIndented(budget.getCategory()).toString();
            String limit = budget.getLimit() == 0 ? "Not set" : "$" + budget.getLimit();
            System.out.printf("%s | $%.2f / %s\n",
                    categoryIndented,
                    budget.getMonthlySpending(entryList),
                    limit);
        }
    }

    public void printBudgetWarningMessage(ExpenseCategory category, double spending, double limit) {
        if (spending > 0.8 * limit && limit != 0) {
            System.out.printf("Slow down, you've set aside $%.2f for %s, "
                    + "but you already spent $%.2f.\n", limit, category, spending);
        }
    }

    public void printUnsafeCharacters() {
        System.out.println("Please do not use special characters. Only '.', '/', '-' are allowed ");
    }
}



