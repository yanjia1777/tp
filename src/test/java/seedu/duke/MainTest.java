package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

class MainTest {
    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    public void addExpense_oneAddition_success() {
        ExpenseList expense= new ExpenseList();
        LocalDate date = LocalDate.now();
        expense.addExpense("burger", date.toString(), "10");
    }

    @Test
    public void addExpense_twoAdditions_success() {
        ExpenseList expense= new ExpenseList();
        LocalDate date = LocalDate.now();
        expense.addExpense("movie", date.toString(), "20");
    }
}
