package seedu.duke;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class CategoryList {
    public static final int CAT_NUM_FOOD = 0;
    public static final int CAT_NUM_ENTERTAINMENT = 1;
    public static final int CAT_NUM_TRANSPORTATION = 2;
    public static final int CAT_NUM_HOUSEHOLD = 3;
    public static final int CAT_NUM_APPAREL = 4;
    public static final int CAT_NUM_BEAUTY = 5;
    public static final int CAT_NUM_GIFT = 6;
    public static final int CAT_NUM_OTHERS = 7;
    private static final String CAT_STR_NO_CAT_FOUND = "No Category found";
    public static final int CAT_NUM_FOOD_INT = 0;
    public static final int CAT_NUM_OTHERS_INT = 7;
    protected static final String ERROR_INVALID_CATNUM = "Please enter a valid category number! c/0 to c/7";
    public static final String CATEGORY_FILE_PATH = "data" + File.separator + "MintCategory.txt";

    protected static ArrayList<Category> categoryList = new ArrayList<>();

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

    public static void viewLimit() {
        LocalDate current = LocalDate.now();
        String currentMonth = current.getMonth().toString();
        int currentYear = current.getYear();
        double totalExpenditure = 0;
        System.out.printf("Here are your expenditures and limit for %s %d%n"
                , currentMonth, currentYear);
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
        DataManager dataManager = new DataManager(CATEGORY_FILE_PATH);
        Category category = CategoryList.categoryList.get(catNumFinal);
        category.setLimit(limit);
        dataManager.editCategoryFileLive(catNumFinal);
    }

    public static void addSpending(String catNum, String amount) {
        int catNumFinal = Integer.parseInt(catNum);
        double amountFinal = Double.parseDouble(amount);
        Category category = CategoryList.categoryList.get(catNumFinal);
        category.addSpending(amountFinal);
        if (category.isNearThreshold()) {
            System.out.printf("Slow down... You've set aside $%,.2f for %s,"
                            + " but you already spent $%,.2f\n"
                    , category.getLimit(), category.getName(), category.getSpending());
        }
    }

    public static void deleteSpending(String catNum, String amount) {
        int catNumFinal = Integer.parseInt(catNum);
        double amountFinal = Double.parseDouble(amount);
        Category category = CategoryList.categoryList.get(catNumFinal);
        category.deleteSpending(amountFinal);
    }

    public static void editSpending(String catNum, String initialAmount, String newAmount) {
        double initialSpending = Double.parseDouble(initialAmount);
        double newSpending = Double.parseDouble(newAmount);
        double difference = Math.abs(initialSpending - newSpending);
        int catNumFinal = Integer.parseInt(catNum);
        Category category = CategoryList.categoryList.get(catNumFinal);
        if (initialSpending > newSpending) {
            category.deleteSpending(difference);
        } else {
            category.addSpending(difference);
        }
    }

    public static void editSpendingCat(int originalCatNum, int newCatNum, String amount) {
        double amountFinal = Double.parseDouble(amount);
        Category oldCategory = CategoryList.categoryList.get(originalCatNum);
        oldCategory.deleteSpending(amountFinal);
        Category newCategory = CategoryList.categoryList.get(newCatNum);
        newCategory.addSpending(amountFinal);
    }
}


