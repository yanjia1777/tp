package seedu.duke;


public class Category {
    private final int catNum;
    private final String name;
    private double spending;
    private double limit;
    private double warningThreshold;

    public int getCatNum() {
        return catNum;
    }

    public String getName() {
        return name;
    }

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

    public double getLimit() {
        return limit;
    }

    public String getIndivCatLimit() {
        if (this.limit != 0) {
            return String.format("$%,.2f", limit);
        }
        return "Not Set";
    }

    public void setLimit(String limit) {
        this.limit = Double.parseDouble(limit);
        setWarningThreshold(this.limit);
    }

    public boolean isNearThreshold() {
        if (limit == 0) {
            return false;
        } else {
            return spending >= warningThreshold;
        }
    }

    public void setWarningThreshold(double limit) {
        this.warningThreshold = 0.8 * limit;
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
        StringBuilder amountWithIndent = new StringBuilder();
        while (leftIndent != 0) {
            amountWithIndent.append(" ");
            leftIndent--;
        }

        amountWithIndent.append(amountString);

        while (rightIndent != 0) {
            amountWithIndent.append(" ");
            rightIndent--;
        }
        return amountWithIndent;
    }

    private String getAmountIndented(String spendingString, double spending) {
        if (spending > 1000) {
            return constructAmountWithIndent(1, 1, spendingString).toString();
        }
        double length = spendingString.length();
        int leftIndent = (int) Math.floor((9 - length) / 2);
        int rightIndent = (int) Math.ceil((9 - length) / 2);
        return constructAmountWithIndent(leftIndent, rightIndent, spendingString).toString();
    }

    public String getSpendingIndented() {
        String spendingString = "$" + spending;
        return getAmountIndented(spendingString, spending);
    }

    public String getLimitIndented() {
        String limitString = getIndivCatLimit();
        return getAmountIndented(limitString, limit);
    }

    public String toString() {
        return getName();
    }

}
