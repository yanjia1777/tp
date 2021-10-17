package seedu.duke;

import java.util.ArrayList;

public class Category {
    public static final int UNLIMITED = 9999;
    private final int catNum;
    private final String name;
    private double limit = UNLIMITED;

    public Category(int catNum, String name) {
        this.catNum = catNum;
        this.name = name;
    }

    public int getCatNum() {
        return catNum;
    }

    public String getName() {
        return name;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    private StringBuilder constructNameIndented(int leftIndent, int rightIndent) {
        StringBuilder nameWithIndent = new StringBuilder();
        while (leftIndent != 0) {
            nameWithIndent.append("");
            leftIndent--;
        }
        nameWithIndent.append(name);
        while (rightIndent != 0) {
            nameWithIndent.append("");
            rightIndent--;
        }
        return nameWithIndent;
    }

    public String getNameIndented() {
        double length = name.length();
        int leftIndent = (int) Math.floor((16 - length)/2);
        int rightIndent = (int) Math.ceil((16 - length)/2);
        return constructNameIndented(leftIndent,rightIndent).toString();
    }

    public String toString() {
        return getName();
    }

}
