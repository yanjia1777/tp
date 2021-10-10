package seedu.duke;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseConstructorTest {
    /**
     * Create a valid Expense object.
     *
     * @result getters for Expense object should tally.
     */
    @Test
    void createExpense_allFieldsValid_success() {
        String name = "Samurai Burger";
        String date = LocalDate.now().toString();
        String amount = "7.50";
        Expense expense = new Expense(name, date, amount);
        assertEquals("Samurai Burger | " + LocalDate.now() + " | $7.50", expense.toString());
    }

    /**
     * Create a valid Expense object.
     *
     * @result getters for Expense object should tally.
     */
    @Test
    void createExpense_EmptyParameters_success() {
        Expense expense = new Expense();
        assertEquals("null | 2021-01-01 | $0.00", expense.toString());
    }


}
