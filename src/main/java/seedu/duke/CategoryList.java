package seedu.duke;

import seedu.duke.storage.CategoryListDataManager;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class CategoryList {
    public static final int CAT_NUM_FOOD_INT = 0;
    public static final int CAT_NUM_OTHERS_INT = 7;
    protected static final String ERROR_INVALID_CATNUM = "Please enter a valid category number! c/0 to c/7";
    public static final String CATEGORY_FILE_PATH = "data" + File.separator + "MintCategory.txt";

    protected static ArrayList<Category> categoryList = new ArrayList<>();

    private static boolean isSameMonth(Expense expense1, Expense expense2) {
        return expense1.getDate().getMonth() == expense2.getDate().getMonth()
                && expense1.getDate().getYear() == expense1.getDate().getYear();
    }

    private static boolean isCurrentMonthExpense(Expense expense) {
        return expense.date.getMonthValue() == LocalDate.now().getMonthValue()
                && expense.date.getYear() == LocalDate.now().getYear();
    }

    public static void addCategory(int catNum, String name) {
        Category category = new Category(catNum, name);
        categoryList.add(category);
    }

    public static void initialiseCategories() {
        addCategory(0, "Food");
        addCategory(1, "Entertainment");
        addCategory(2, "Transportation");
        addCategory(3, "Household");
        addCategory(4, "Apparel");
        addCategory(5, "Beauty");
        addCategory(6, "Gift");
        addCategory(7, "Others");
    }

    public static void viewCategories() {
        System.out.println("Here are the categories and its corresponding numbers");
        for (Category category : categoryList) {
            System.out.println("c/" + category.getCatNum() + " " + category.getName());
        }
    }

    public static void viewMonthlyLimit() {
        LocalDate current = LocalDate.now();
        String currentMonth = current.getMonth().toString();
        int currentYear = current.getYear();
        double totalExpenditure = 0;
        System.out.printf("Here are your expenditures and limit for %s %d%n",
                currentMonth, currentYear);
        for (Category category : categoryList) {
            int catNum = category.getCatNum();
            totalExpenditure += category.getSpending();
            System.out.println(getSpendingIndented(catNum) + "/"
                    + getLimitIndented(catNum) + " | c/"
                    + catNum + " " + category.getName());
        }
        System.out.printf("Total expenditure for this month: $%,.2f\n", totalExpenditure);
    }

    public static String getCatName(int catNum) {
        Category category = CategoryList.categoryList.get(catNum);
        return category.getName();
    }


    public static String getCatNameIndented(int catNum) {
        Category category = CategoryList.categoryList.get(catNum);
        return category.getNameIndented();
    }

    public static String getLimitIndented(int catNum) {
        Category category = CategoryList.categoryList.get(catNum);
        return category.getLimitIndented();
    }

    public static String getSpendingIndented(int catNum) {
        Category category = CategoryList.categoryList.get(catNum);
        return category.getSpendingIndented();
    }

    public static void checkValidCatNum(String catNum) throws MintException {
        int catNumInt = Integer.parseInt(catNum);
        try {
            if (catNumInt < CAT_NUM_FOOD_INT || catNumInt > CAT_NUM_OTHERS_INT) {
                throw new MintException(ERROR_INVALID_CATNUM);
            }
        } catch (NumberFormatException e) {
            throw new MintException(ERROR_INVALID_CATNUM);
        }
    }

    public static void setLimit(String catNum, String limit) throws MintException {
        int catNumFinal = Integer.parseInt(catNum);
        CategoryListDataManager dataManager = new CategoryListDataManager(CATEGORY_FILE_PATH);
        Category category = CategoryList.categoryList.get(catNumFinal);
        category.setLimit(limit);
        dataManager.editCategoryTextFile(catNumFinal);
    }

    public static void addSpending(Expense expense) {
        Category category = CategoryList.categoryList.get(expense.catNum);
        category.addSpending(expense.amount);
        if (category.isNearThreshold()) {
            System.out.printf("Slow down... You've set aside $%,.2f for %s,"
                            + " but you already spent $%,.2f\n",
                    category.getLimit(), category.getName(), category.getSpending());
        }
    }

    public static void deleteSpending(Expense expense) {
        Category category = CategoryList.categoryList.get(expense.catNum);
        category.deleteSpending(expense.amount);
    }

    public static void editSpending(Expense originalExpense, Expense newExpense) {
        /*
         * Case 1: current month --> current month
         * Case 2: other months --> current month
         * Case 3: current month --> other months
         * Case 4: other months --> other months, do nothing
         */
        if (isSameMonth(originalExpense, newExpense) && isCurrentMonthExpense(newExpense)) {
            deleteSpending(originalExpense);
            addSpending(newExpense);
        } else if (!isSameMonth(originalExpense, newExpense) && isCurrentMonthExpense(newExpense)) {
            addSpending(newExpense);
        } else if (!isSameMonth(originalExpense, newExpense) && isCurrentMonthExpense(originalExpense)) {
            deleteSpending(originalExpense);
        }
    }
}


