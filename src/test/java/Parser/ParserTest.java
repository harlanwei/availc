package Parser;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void parseWithoutUsernameOrPassword() throws IOException, URISyntaxException {
        try (Parser p = new Parser()) {
            Set<String> classrooms = new HashSet<>();
            classrooms.add("J4-101");
            Map<String, Boolean[]> result = p.isAvailable(classrooms);
            assertFalse(result.get("j4-101")[1]);
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}