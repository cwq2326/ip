package duke;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void testInvalidInput() {
        try {
            Parser.parse("invalid input");
        } catch (DukeException err) {
            assertEquals("I'm so very sorry, " +
                            "please make sure you enter a valid Ekud command",
                    err.getMessage());
        }
    }

    @Test
    public void testEmptyInPut() {
        try {
            Parser.parse("");
        } catch (DukeException err) {
            assertEquals("☹ OOPS!!! Please input a command",
                    err.getMessage());
        }
    }

    @Test
    public void testEmptyDescription() {
        try {
            Parser.parse("todo");
        } catch (DukeException err) {
            assertEquals("I'm so very sorry, the description of a todo cannot be empty.",
                    err.getMessage());
        }
    }
}
