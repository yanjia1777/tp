package seedu.duke;


import java.time.LocalDate; // import the LocalDate class
import java.util.Objects;

public class Expense extends Entry {
    protected ExpenseCategory category;
    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");
    public static final int CAT_NUM_OTHERS = 7;

    public Expense() {
        super();
        this.type = Type.Expense;
    }

    public Expense(String name, String date, String amount, String catNum) {
        super(name, date, amount, catNum);
        this.type = Type.Expense;
    }

    public Expense(String name, String date, String amount) {
        super(name, date, amount);
        this.type = Type.Expense;
    }
  
  
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String toString() {
        return getType() + " | " + getCatIndent() + " | " + getDate() + " | "
                + getNameIndented() + " |-$" + String.format("%,.2f", getAmount());

    }
}
