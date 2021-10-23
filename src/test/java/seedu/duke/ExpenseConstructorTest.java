<<<<<<< HEAD
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
        LocalDate date = LocalDate.parse("2021-10-12");
        Double amount = 7.50;
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        assertEquals("FOOD | 2021-10-12 | Samurai Burger | $7.50", expense.toString());
    }
}
=======
//package seedu.duke;
//
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ExpenseConstructorTest {
//    /**
//     * Create a valid Expense object.
//     *
//     * @result getters for Expense object should tally.
//     */
//    @Test
//    void createExpense_allFieldsValid_success() {
//        String name = "Samurai Burger";
//        String date = "2021-10-12";
//        String amount = "7.50";
//        String catNum = "0";
//        CategoryList.initialiseCategories();
//        Expense expense = new Expense(name, date, amount, catNum);
//        assertEquals("Food | 2021-10-12 | Samurai Burger | $7.50", expense.toString());
//    }
//}
>>>>>>> upstream/master
