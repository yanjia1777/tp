package seedu.duke;

import java.util.ArrayList;

public class CategoryList {
    protected ArrayList<Category> categoryList = new ArrayList<>();

    public void setCategory(int catNum, String name) {
        Category category = new Category(catNum, name);
        categoryList.add(category);
    }

    public void initCategories() {
        this.setCategory(0, "Food");
        this.setCategory(1, "Entertainment");
        this.setCategory(2, "Transportation");
        this.setCategory(3, "Household");
        this.setCategory(4, "Apparel");
        this.setCategory(5, "Beauty");
        this.setCategory(6, "Gift");
        this.setCategory(7,"Others");
    }

    public void viewCategories() {
        System.out.println("Here are the categories and its corresponding numbers");
        for (Category category : categoryList) {
            System.out.println("c/" + category.getCatNum() + " " + category.getName());
        }
    }
}
