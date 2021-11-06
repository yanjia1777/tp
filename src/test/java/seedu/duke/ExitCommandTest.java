package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.HelpCommand;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExitCommandTest {
    @Test
    void getBooleanExitCommand_validInput_returnTrue() {
        ExitCommand exitCommand = new ExitCommand();
        assertTrue(exitCommand.isExit());
    }

    @Test
    void getBooleanAnyOtherCommand_validInput_returnFalse() {
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.isExit());
    }

}
