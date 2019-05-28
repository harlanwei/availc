package Resources;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Loader {
    public static File getResource(String filePath) throws IOException {
        try {
            URL url = Loader.class.getResource(filePath);
            if (url == null) {
                throw new IllegalArgumentException("The request file does not exist.");
            }
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            // It's almost impossible to have {@code URISyntaxException}
            // thrown here, so we just do nothing.
        }

        throw new IOException("Failed to get the requested resource.");
    }

    private Loader() {
    }
}
