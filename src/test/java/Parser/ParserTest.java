package Parser;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void parseADocument() throws IOException, URISyntaxException {
        Map<String, Boolean[]> results = new HashMap<>();
        try (Parser p = new Parser(false)) {
            URL resource = ParserTest.class.getResource("/test.html");
            File file = Paths.get(resource.toURI()).toFile();
            p.parse(results, file);
            assertEquals(true, results.get("J4-101")[0]);
        }
    }
}