package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.utility.Ui;

import javax.sound.midi.SysexMessage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {
    @Test
    void userInput_containsSpecialChar_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui ui = new Ui();
        if (ui.hasUnsafeCharacters("+")) {
            ui.printUnsafeCharacters();
        }
        String expectedOutput = "Please do not use special characters. "
                + "Only '.', '/', '-' are allowed. '/' is strictly use for tags."
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void uiSetBudget_correctType_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui ui = new Ui();
        ui.printSetBudget(ExpenseCategory.FOOD, 10);
        String expectedOutput =  "Budget for FOOD set to $10.00\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
