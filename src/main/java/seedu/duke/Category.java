package seedu.duke;


public class Category {
    private final int catNum;
    private double spending;
    private final String name;
    private double limit;

    public double getSpending() {
        return spending;
    }

    public void addSpending(double spending) {
        this.spending += spending;
    }

    public void deleteSpending(double spending) {
        this.spending -= spending;
    }

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

    public String getLimitCat() {
        if (this.limit != 0) {
            return String.format("$%,.2f", limit);
        }
        return "Not Set";
    }

    public void setLimit(String limit) {
        this.limit = Double.parseDouble(limit);
    }

    private StringBuilder constructNameWithIndent(int leftIndent, int rightIndent) {
        StringBuilder nameWithIndent = new StringBuilder();
        while (leftIndent != 0) {
            nameWithIndent.append(" ");
            leftIndent--;
        }

        nameWithIndent.append(name);

        while (rightIndent != 0) {
            nameWithIndent.append(" ");
            rightIndent--;
        }
        return nameWithIndent;
    }

    public String getNameIndented() {
        double length = name.length();
        int leftIndent = (int) Math.floor((16 - length) / 2);
        int rightIndent = (int) Math.ceil((16 - length) / 2);
        return constructNameWithIndent(leftIndent, rightIndent).toString();
    }

    private StringBuilder constructAmountWithIndent(int leftIndent, int rightIndent, String amountString) {
        StringBuilder nameWithIndent = new StringBuilder();
        while (leftIndent != 0) {
            nameWithIndent.append(" ");
            leftIndent--;
        }

        nameWithIndent.append(amountString);

        while (rightIndent != 0) {
            nameWithIndent.append(" ");
            rightIndent--;
        }
        return nameWithIndent;
    }

    public String getSpendingIndented() {
        String spendingString = "$" + spending;
        if (limit > 1000) {
            return constructAmountWithIndent(1, 1, spendingString).toString();
        }
        double length = spendingString.length();
        int leftIndent = (int) Math.floor((9 - length) / 2);
        int rightIndent = (int) Math.ceil((9 - length) / 2);
        return constructAmountWithIndent(leftIndent, rightIndent, spendingString).toString();
    }

    public String getLimitIndented() {
        String limitString = getLimitCat();
        if (limit > 1000) {
            return constructAmountWithIndent(1, 1, limitString).toString();
        }
        double length = limitString.length();
        int leftIndent = (int) Math.floor((9 - length) / 2);
        int rightIndent = (int) Math.ceil((9 - length) / 2);
        return constructAmountWithIndent(leftIndent, rightIndent, limitString).toString();
    }


    public String toString() {
        return getName();
    }

}
