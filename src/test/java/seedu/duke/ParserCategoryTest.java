package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserCategoryTest {
    @Test
    void addFoodExpense_validInput_categoryIsFood() {
        Parser parser = new Parser();
        parser.parseCommand("add n/food a/10 c/0");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.FOOD);
    }

    @Test
    void addEntertainmentExpense_validInput_categoryIsEntertainment() {
        Parser parser = new Parser();
        parser.parseCommand("add n/entertainment a/10 c/1");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.ENTERTAINMENT);
    }

    @Test
    void addTransportationExpense_validInput_categoryIsTransportation() {
        Parser parser = new Parser();
        parser.parseCommand("add n/transportation a/10 c/2");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.TRANSPORTATION);
    }

    @Test
    void addHouseholdExpense_validInput_categoryIsHousehold() {
        Parser parser = new Parser();
        parser.parseCommand("add n/household a/10 c/4");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.HOUSEHOLD);
    }
    
    @Test
    void addApparelExpense_validInput_categoryIsApparel() {
        Parser parser = new Parser();
        parser.parseCommand("add n/apparel a/10 c/4");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.APPAREL);
    }

    @Test
    void addBeautyExpense_validInput_categoryIsBeauty() {
        Parser parser = new Parser();
        parser.parseCommand("add n/beauty a/10 c/5");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.BEAUTY);
    }

    @Test
    void addGiftExpense_validInput_categoryIsGift() {
        Parser parser = new Parser();
        parser.parseCommand("add n/gift a/10 c/6");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.GIFT);
    }

    @Test
    void addOthersExpense_validInput_categoryIsOthers() {
        Parser parser = new Parser();
        parser.parseCommand("add n/others a/10 c/7");
        assertEquals(parser.getExpenseCategory(),ExpenseCategory.OTHERS);
    }
    
}
