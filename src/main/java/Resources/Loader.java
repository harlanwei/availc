package Resources;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Loader {
    public static File getResource(String filePath, boolean createIfNotExist) throws IOException {
        try {
            URI uri = Loader.class.getResource(filePath).toURI();
            File file = new File(uri);
            if (!file.exists()) {
                if (createIfNotExist) {
                    if (!file.createNewFile())
                        throw new IOException("Failed to get the requested resource.");
                } else {
                    throw new IllegalArgumentException("The requested file does not exist.");
                }
            }
            return file;
        } catch (URISyntaxException e) {
            // It's almost impossible to have {@code URISyntaxException}
            // thrown here, so we just do nothing.
        }

        throw new IOException("Failed to get the requested resource.");
    }

    private Loader() {
    }
}
