package seedu.duke;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    void parseUserInput_fieldsWithWhitespaces_NoException() throws MintException {
        String sampleInput = "add   n/burger       d/2020-12-12   a/15";
        Parser parser = new Parser();
        parser.parseInput(sampleInput);
    }
}
