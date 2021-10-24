package seedu.duke;

import java.time.LocalDate; // import the LocalDate class
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Income extends Entry {
    protected ExpenseCategory category;

    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");
    public static final int CAT_NUM_OTHERS = 7;

    public Income() {
        super();
        this.type = Type.Income;
    }

    public Income(String name, String date, String amount, String catNum) {
        super(name, date, amount, catNum);
        this.type = Type.Income;
    }

    public Income(String name, String date, String amount) {
        super(name, date, amount);
        this.type = Type.Income;
    }
  
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String toString() {
        return getType() + "  | " + getCatIndent() + " | " + getDate() + " | "
                + getNameIndented() + " | $" + String.format("%,.2f", getAmount());
    }
}
