package seedu.duke;

import seedu.duke.parser.Parser;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecurringExpenseList {
    public static final String STRING_PROMPT_EDIT = "What would you like to edit?";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final int LENGTH_OF_SEPARATOR = 2;
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String INTERVAL_SEPARATOR = "i/";
    public static final String BLANK = "";
    protected ArrayList<RecurringExpense> recurringExpenseList = new ArrayList<>();
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";

    public void addRecurringExpense(String name, String date, String amount,
                                    String catNum, String interval, String endDate) throws MintException {
        try {
            RecurringExpense expense = new RecurringExpense(name, date, amount, catNum, interval, endDate);
            logger.log(Level.INFO, "User added expense: " + expense);
            System.out.println("I have added: " + expense);
            recurringExpenseList.add(expense);
            //ExpenseListDataManager.appendToExpenseListTextFile(FILE_PATH, expense);
        //} catch (IOException e) {
        //    System.out.println("Error trying to update external file!");
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public ArrayList<Entry> filterRecurringExpenseByKeywords(ArrayList<String> tags, String name,
                                                               String date, String amount, String catNum,
                                                               String interval) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(recurringExpenseList);
        for (String tag : tags) {
            switch (tag) {
            case "n/":
                filteredList = Filter.filterEntryByName(name, filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(date, filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(amount, filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCatNum(catNum, filteredList);
                break;
            case "i/":
                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    public void deleteRecurringExpenseByKeywords(ArrayList<String> tags, String name,
                                 String date, String amount, String catNum, String interval) throws MintException {
        try {
            RecurringExpense expense = chooseRecurringExpenseByKeywords(tags, true, name,
                    date, amount, catNum, interval);
            if (expense != null) {
                deleteRecurringExpense(expense);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public void editRecurringExpenseByKeywords(ArrayList<String> tags, String name, String date,
                                               String amount, String catNum, String interval) throws MintException {
        try {
            RecurringExpense expense = (RecurringExpense) chooseRecurringExpenseByKeywords(tags, true, name,
                    date, amount, catNum, interval);
            if (expense != null) {
                editRecurringExpense(expense);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public RecurringExpense chooseRecurringExpenseByKeywords(ArrayList<String> tags, boolean isDelete,
                                                             String name, String date, String amount,
                                                             String catNum, String interval) throws MintException {
        ArrayList<Entry> filteredList = filterRecurringExpenseByKeywords(tags, name, date, amount, catNum, interval);
        RecurringExpense expense = null;
        if (filteredList.size() == 0) {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        } else if (filteredList.size() == 1) {
            RecurringExpense onlyExpense = (RecurringExpense) filteredList.get(0);
            if (Ui.isConfirmedToDeleteOrEdit(onlyExpense, isDelete)) {
                expense = onlyExpense;
            }
            return expense;
        }

        Ui.viewGivenList(filteredList);
        try {
            int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
            if (index >= 0) {
                expense = (RecurringExpense) filteredList.get(index);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return expense;
    }

    public void deleteRecurringExpense(RecurringExpense expense) throws MintException {
        deleteRecurringExpense(expense.getName(), expense.getDate().toString(),
                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()),
                expense.getInterval().label, expense.getEndDate().toString());
    }

    public void deleteRecurringExpense(String name, String date, String amount,
                                       String catNum, String interval, String endDate) throws MintException {
        RecurringExpense expense = new RecurringExpense(name, date, amount, catNum, interval, endDate);
        if (recurringExpenseList.contains(expense)) {
            logger.log(Level.INFO, "User deleted recurring expense: " + expense);
            System.out.println("I have deleted: " + expense);
            recurringExpenseList.remove(expense);
            String stringToDelete = overWriteString(expense);
            //ExpenseListDataManager.deleteLineInTextFile(stringToDelete);
        }
    }

    public void viewRecurringExpense(String[] argumentArrayInput) throws MintException {
        String sortType;
        LocalDate fromDate;
        LocalDate endDate;
        Month month;
        String year = null;
        ArrayList<String> argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));
        ArrayList<Expense> outputArray = new ArrayList<Expense>(recurringExpenseList);

        for (Expense expense : outputArray) {
            System.out.println(expense.viewToString());
        }
    }

    public void viewRecurringExpenseByMonth(ArrayList<Entry> expenseList, int month, int year) {
        for (RecurringExpense expense : recurringExpenseList) {
            YearMonth startYM = YearMonth.from(expense.getDate());
            YearMonth endYM = YearMonth.from(expense.getEndDate());
            YearMonth currentYM = YearMonth.of(year, month);
            int startY = expense.getDate().getYear();
            int endY = expense.getEndDate().getYear();

            switch (expense.getInterval()) {
            case MONTH:
                boolean isYearMonthBetweenStartAndEnd = startYM.compareTo(currentYM) <= 0
                        && currentYM.compareTo(endYM) <= 0;
                if (isYearMonthBetweenStartAndEnd) {
                    RecurringExpense newExpense = new RecurringExpense(expense);
                    newExpense.setDate(currentYM.atDay(expense.getDate().getDayOfMonth()));
                    expenseList.add(newExpense);
                }
                break;
            case YEAR:
                boolean isSameMonthAsStart = startYM.getMonth() == currentYM.getMonth();
                boolean isYearBetweenStartAndEnd = startY <= year && year <= endY;
                if (isSameMonthAsStart && isYearBetweenStartAndEnd) {
                    RecurringExpense newExpense = new RecurringExpense(expense);
                    newExpense.setDate(currentYM.atDay(expense.getDate().getDayOfMonth()));
                    expenseList.add(newExpense);
                }
                break;
            default:
                break;
            }
        }
    }

    public void viewRecurringExpenseByYear(ArrayList<Entry> expenseList, int year) {
        for (RecurringExpense expense : recurringExpenseList) {
            YearMonth startRecurringYM = YearMonth.from(expense.getDate());
            YearMonth endRecurringYM = YearMonth.from(expense.getEndDate());
            int startY = expense.getDate().getYear();
            int endY = expense.getEndDate().getYear();

            switch (expense.getInterval()) {
            case MONTH:
                YearMonth iteratorYM = YearMonth.of(year, Month.JANUARY);
                YearMonth endLoopYM = YearMonth.of(year, Month.DECEMBER);
                while (iteratorYM.compareTo(endLoopYM) <= 0) {
                    boolean isBetweenRecurringPeriod = iteratorYM.compareTo(startRecurringYM) >= 0
                            && iteratorYM.compareTo(endRecurringYM) <= 0;
                    if (isBetweenRecurringPeriod) {
                        RecurringExpense newExpense = new RecurringExpense(expense);
                        newExpense.setDate(iteratorYM.atDay(expense.getDate().getDayOfMonth()));
                        expenseList.add(newExpense);
                    }
                    iteratorYM = iteratorYM.plusMonths(1);
                }
                break;
            case YEAR:
                boolean isYearBetweenStartAndEnd = startY <= year && year <= endY;
                if (isYearBetweenStartAndEnd) {
                    YearMonth billYM = YearMonth.of(year, startRecurringYM.getMonthValue());
                    expense.setDate(billYM.atDay(expense.getDate().getDayOfMonth()));
                    expenseList.add(expense);
                }
                break;
            default:
                break;
            }
        }
    }

    public void viewRecurringExpenseBetweenTwoDates(ArrayList<Entry> expenseList, LocalDate startDate,
                                                    LocalDate endDate) {
        for (RecurringExpense expense : recurringExpenseList) {
            LocalDate startRecurringDate = expense.getDate();
            int startRecurringYear = expense.getDate().getYear();
            int endRecurringYear = expense.getEndDate().getYear();
            int endYear = endDate.getYear();
            YearMonth startRecurringYM = YearMonth.from(expense.getDate());
            YearMonth endRecurringYM = YearMonth.from(expense.getEndDate());
            YearMonth endYM = YearMonth.from(endDate);

            switch (expense.getInterval()) {
            case MONTH:
                YearMonth iteratorYM = startRecurringYM;
                YearMonth endLoopYM = endYM.isBefore(endRecurringYM) ? endYM : endRecurringYM;
                while (iteratorYM.compareTo(endLoopYM) <= 0) {
                    LocalDate currentDate = iteratorYM.atDay(expense.getDate().getDayOfMonth());
                    if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                        RecurringExpense newExpense = new RecurringExpense(expense);
                        newExpense.setDate(iteratorYM.atDay(expense.getDate().getDayOfMonth()));
                        expenseList.add(newExpense);
                    }
                    iteratorYM = iteratorYM.plusMonths(1);
                }
                break;
            case YEAR:
                int effectiveEndYear = Math.min(endRecurringYear, endYear);
                for (int i = startRecurringYear; i <= effectiveEndYear; i++) {
                    LocalDate currentDate = LocalDate.of(i, startRecurringDate.getMonthValue(),
                            startRecurringDate.getDayOfMonth());
                    if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                        RecurringExpense newExpense = new RecurringExpense(expense);
                        newExpense.setDate(currentDate);
                        expenseList.add(newExpense);
                    }
                }
                break;
            default:
                break;
            }
        }
    }

    public void sort(ArrayList<Expense> outputArray, String sortType) throws MintException {
        assert sortType != null : "sortType should have a command";
        switch (sortType) {
        case "name":
            outputArray.sort(Sorter.compareByName);
            break;
        case "date":
            outputArray.sort(Sorter.compareByDate);
            break;
        case "amount":
            outputArray.sort(Sorter.compareByAmount);
            break;
        case "category":
            outputArray.sort(Sorter.compareByCategory);
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_COMMAND);
        }
    }

    public void editRecurringExpense(RecurringExpense expense) throws MintException {
        editRecurringExpense(expense.getName(), expense.getDate().toString(),
                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()),
                expense.getInterval().label, expense.getEndDate().toString());
    }

    public void editRecurringExpense(String name, String date, String amount,
                                     String catNum, String interval, String endDate) throws MintException {
        String choice;
        int indexToBeChanged;
        boolean printEditSuccess = false;
        boolean exceptionThrown = false;
        try {
            RecurringExpense originalExpense = new RecurringExpense(name, date, amount, catNum, interval, endDate);
            final String originalExpenseStr = originalExpense.toString();
            final String stringToOverwrite = overWriteString(originalExpense);
            if (recurringExpenseList.contains(originalExpense)) {
                indexToBeChanged = recurringExpenseList.indexOf(originalExpense);
                choice = scanFieldsToUpdate();
            } else {
                logger.log(Level.INFO, "User entered invalid entry");
                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
            }
            editSpecifiedEntry(choice, indexToBeChanged, originalExpense);
            printEditSuccess = isEditSuccessful(indexToBeChanged, originalExpenseStr);
            String stringToUpdate = overWriteString(recurringExpenseList.get(indexToBeChanged));
            final RecurringExpense newExpense = recurringExpenseList.get(indexToBeChanged);
            CategoryList.editSpending(originalExpense, newExpense);
            //ExpenseListDataManager.editExpenseListTextFile(stringToOverwrite, stringToUpdate);

        } catch (NumberFormatException e) {
            exceptionThrown = true;
            System.out.println(MintException.ERROR_INVALID_NUMBER);
        } catch (DateTimeParseException e) {
            exceptionThrown = true;
            System.out.println(MintException.ERROR_INVALID_DATE_EDIT);
        }
        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
    }

    public static String overWriteString(Expense expense) {
        return expense.getCatNum() + "|" + expense.getDate() + "|" + expense.getName()
                + "|" + expense.getAmount();
    }

    private void editSpecifiedEntry(String userInput, int indexToBeChanged,
                                    RecurringExpense expense) throws MintException {
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
        amendRecurringExpense(indexToBeChanged, splitChoice, expense);
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

    private Boolean isEditSuccessful(int indexToBeChanged, String originalExpense) {
        String newExpense = recurringExpenseList.get(indexToBeChanged).toString();
        return !originalExpense.equals(newExpense);
    }

    private void amendRecurringExpense(int index, ArrayList<String> choice,
                                       RecurringExpense expense) throws MintException {
        String name = expense.getName();
        String date = expense.getDate().toString();
        String amount = Double.toString(expense.getAmount());
        String catNum = Integer.toString(expense.getCatNum());
        String interval = expense.getInterval().label;
        String endDate = expense.getEndDate().toString();
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
            if (word.contains(INTERVAL_SEPARATOR)) {
                amount = word.substring(word.indexOf(INTERVAL_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
            }
        }
        recurringExpenseList.set(index, new RecurringExpense(name, date, amount, catNum, interval, endDate));
    }

    private String nonEmptyNewDescription(String word) throws MintException {
        String description;
        String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
        if (!newDescription.trim().equalsIgnoreCase(BLANK)) {
            description = newDescription.trim();
        } else {
            throw new MintException(MintException.ERROR_INVALID_DESCRIPTION);
        }
        return description;
    }

    public ArrayList<RecurringExpense> getExpenseList() {
        return recurringExpenseList;
    }
}
