package Parser;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void parseADocument() throws IOException {
        Map<String, Boolean[]> results = new HashMap<>();
        try (Parser p = new Parser(false)) {
            p.parse(results, new File("C:\\Users\\Vian\\Downloads\\test.html"));
            assertEquals(true, results.get("J4-101")[0]);
        }
    }
}