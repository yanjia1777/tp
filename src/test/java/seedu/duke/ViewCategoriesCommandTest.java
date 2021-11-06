package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.utility.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewCategoriesCommandTest {
    @Test
    void viewCategoryCommandTest_validInput_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui ui = new Ui();
        ui.printCategoryList();
        String expectedOutput = "Here are the categories and its tag number\n"
                + "Expenses           | Income\n"
                + "c/0 FOOD           | c/0 ALLOWANCE\n"
                + "c/1 ENTERTAINMENT  | c/1 WAGES\n"
                + "c/2 TRANSPORTATION | c/2 SALARY\n"
                + "c/3 HOUSEHOLD      | c/3 INTERESTED\n"
                + "c/4 APPAREL        | c/4 INVESTMENT\n"
                + "c/5 BEAUTY         | c/5 COMMISSION\n"
                + "c/6 GIFT           | c/6 GIFT\n"
                + "c/7 OTHERS         | c/7 OTHERS\n"
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
