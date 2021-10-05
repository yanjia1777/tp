package seedu.duke;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseConstructorTest {
    /**
     * Create a valid Expense object
     *
     * @result getters for Expense object should tally
     */
    @Test
    void createExpense_allFieldsValid_success() {
        int catNum = 0; //food
        String description = "Samurai Burger";
        double amount = 7.50;
        LocalDate date = LocalDate.now();
        Expense expense = new Expense(catNum, description, amount);
        assertEquals(expense.getCatNum(), 0); //0 for food
        assertEquals(date + " | " + "Food | $7.50 | Samurai Burger", expense.toString());
        assertEquals(expense.getDate(), date);
        assertFalse(expense.isMonthly()); //false by default
    }

    /**
     * Create a valid Expense object
     *
     * @result getters for Expense object should tally
     */
    @Test
    void createExpense_EmptyParameters_success() {
        Expense expense = new Expense();
        assertEquals( "2021-01-01 | Other | $0.00 | none", expense.toString());
    }
}
