package seedu.duke;

import java.time.LocalDate; // import the LocalDate class
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Income extends Entry {
    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");
    public static final int CAT_NUM_OTHERS = 7;
    private int catNum;
    private String name;
    private LocalDate date;
    private double amount;

    public Income() {
        catNum = CAT_NUM_OTHERS; //others
        date = LocalDate.of(2021, 1, 1);
        amount = 0;
    }

    public Income(String name, String date, String amount, String catNum) {
        this.catNum = Integer.parseInt(catNum);
        this.name = name;
        this.date = LocalDate.parse(date, dateFormatter);
        this.amount = Double.parseDouble(amount);
    }

    public Income(String name, String date, String amount) {
        this.catNum = CAT_NUM_OTHERS;
        this.name = name;
        this.date = LocalDate.parse(date, dateFormatter);
        this.amount = Double.parseDouble(amount);
    }
}
