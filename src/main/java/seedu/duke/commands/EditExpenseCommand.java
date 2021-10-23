package seedu.duke.commands;


import seedu.duke.CategoryList;
import seedu.duke.Expense;
import seedu.duke.ExpenseList;
import seedu.duke.MintException;
import seedu.duke.storage.ExpenseListDataManager;
import seedu.duke.parser.Parser;
import seedu.duke.Ui;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditExpenseCommand {

    public static final String STRING_PROMPT_EDIT = "What would you like to edit?";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final String BLANK = "";
    public static final int LENGTH_OF_SEPARATOR = 2;
    protected static final String ERROR_EXPENSE_NOT_IN_LIST = "Hmm.. That item is not in the list.";


    ExpenseList expenseList = new ExpenseList();

    void editExpenseByKeywords(ArrayList<String> tags, String name,
                               String date, String amount, String catNum,
                               ArrayList<Expense> dummyExpenseList) throws MintException {
        try {
            Expense expense = expenseList.chooseExpenseByKeywords(tags, true, name, date, amount, catNum);
            if (expense != null) {
                editExpense(expense, dummyExpenseList);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }


    //    public void editExpense(Expense expense) throws MintException {
    //        editExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }

    public void editExpense(Expense expense, ArrayList<Expense> expenseList) throws MintException {
        String choice;
        int indexToBeChanged;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;
        try {
            String name = expense.getName();
            String date = expense.getDate().toString();
            String amount = Double.toString(expense.getAmount());
            String catNum = Integer.toString(expense.getCatNum());
            Expense originalExpense = new Expense(name, date, amount, catNum);
            final String originalExpenseStr = originalExpense.toString();
            final String stringToOverwrite = ExpenseList.overWriteString(originalExpense);
            if (expenseList.contains(originalExpense)) {
                indexToBeChanged = expenseList.indexOf(originalExpense);
                choice = scanFieldsToUpdate();
            } else {
                //                logger.log(Level.INFO, "User entered invalid entry");
                throw new MintException(ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
            }
            editSpecifiedEntry(choice, indexToBeChanged, originalExpense, expenseList);
            // edited
            printEditSuccess = isEditSuccessful(indexToBeChanged, originalExpenseStr, expenseList);
            String stringToUpdate = ExpenseList.overWriteString(expenseList.get(indexToBeChanged));
            final Expense newExpense = expenseList.get(indexToBeChanged);
            CategoryList.editSpending(originalExpense, newExpense);
            ExpenseListDataManager.editExpenseListTextFile(stringToOverwrite, stringToUpdate);

        } catch (NumberFormatException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_NUMBER);
        } catch (DateTimeParseException e) {
            exceptionThrown = true;
            System.out.println(ERROR_INVALID_DATE);
        }
        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
    }

    private void editSpecifiedEntry(String userInput, int indexToBeChanged, Expense expense,
                                    ArrayList<Expense> expenseList) throws MintException {
        Parser parser = new Parser();
        ArrayList<String> splitChoice = new ArrayList<>();
        String choice = " " + userInput;
        while (choice.matches(userTagRaw)) {
            int currentIndex = parser.getCurrentTagIndex(choice);
            int nextIndex = choice.length();

            if (parser.hasNextTag(choice, currentIndex)) {
                nextIndex = parser.getNextTagIndex(choice, currentIndex);
            }
            choice = remainingString(splitChoice, choice, currentIndex, nextIndex);
        }
        amendExpense(indexToBeChanged, splitChoice, expense, expenseList);
    }

    private String remainingString(ArrayList<String> splitChoice, String choice, int currentIndex, int nextIndex) {
        String description;
        description = choice.substring(currentIndex, nextIndex).trim();
        extractFieldsToAmend(splitChoice, description);
        choice = choice.substring(nextIndex);
        return choice;
    }

    private void extractFieldsToAmend(ArrayList<String> splitChoice, String description) {
        splitChoice.add(description);
    }

    private String scanFieldsToUpdate() {
        String choice;
        Scanner scan = new Scanner(System.in);
        System.out.println(STRING_PROMPT_EDIT);
        choice = scan.nextLine();
        return choice;
    }

    private Boolean isEditSuccessful(int indexToBeChanged, String originalExpense, ArrayList<Expense> expenseList) {
        String newExpense = expenseList.get(indexToBeChanged).toString();
        return !originalExpense.equals(newExpense);
    }

    private void amendExpense(int index, ArrayList<String> choice, Expense expense,
                              ArrayList<Expense> expenseList) throws MintException {
        String name = expense.getName();
        String date = expense.getDate().toString();
        String amount = Double.toString(expense.getAmount());
        String catNum = Integer.toString(expense.getCatNum());
        for (String word : choice) {
            assert (word != null);
            if (word.contains(NAME_SEPARATOR)) {
                name = nonEmptyNewDescription(word);
            }
            if (word.contains(DATE_SEPARATOR)) {
                date = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(AMOUNT_SEPARATOR)) {
                amount = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
            if (word.contains(CATEGORY_SEPARATOR)) {
                catNum = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                CategoryList.checkValidCatNum(catNum);
            }
        }
        expenseList.set(index, new Expense(name, date, amount, catNum));
    }

    private String nonEmptyNewDescription(String word) throws MintException {
        String description;
        String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
        if (!newDescription.trim().equalsIgnoreCase(BLANK)) {
            description = newDescription.trim();
        } else {
            throw new MintException(ERROR_INVALID_DESCRIPTION);
        }
        return description;
    }
}
