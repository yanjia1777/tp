package seedu.duke;

import java.util.ArrayList;

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

    private static int checkValidCatNum(int catNum) {
        if (!(catNum >= CAT_NUM_FOOD && catNum <= CAT_NUM_OTHERS)) {
            catNum = CAT_NUM_OTHERS;
        }
        return catNum;
    }

    public static String getCatName(int catNum) {
        catNum = checkValidCatNum(catNum);
        Category category = CategoryList.categoryList.get(catNum);
        return category.getName();
    }


    public static String getCatNameIndented(int catNum) {
        catNum = checkValidCatNum(catNum);
        Category category = CategoryList.categoryList.get(catNum);
        return category.getNameIndented();
    }
}


