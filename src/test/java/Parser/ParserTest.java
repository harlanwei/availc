package Parser;

import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void parseWithoutUsernameOrPassword() {
        try (Parser p = new Parser()) {
            Set<String> classrooms = new HashSet<>();
            classrooms.add("J4-101");
            Map<String, boolean[]> result = p.isAvailable(classrooms);
            assertFalse(result.get("j4-101")[1]);
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    @Test
    public void tryToGetRoomsInTheBuilding() {
        try (Parser p = new Parser()) {
            Set<String> classroom = p.getRoomsInTheBuilding("x1");
            assertNotNull(classroom);
            System.out.println(classroom);
        }
    }
}