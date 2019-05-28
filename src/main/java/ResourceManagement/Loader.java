package ResourceManagement;

import java.io.InputStream;


public class Loader {
    /**
     * Get resources from the {@code resources} folder. Note that this method only gets you the
     * static resources. For dynamic resources such as the configuration files, please refer to
     * the {@code Parser::Cache} class.
     */
    public static InputStream getResource(String filePath) {
        InputStream fileStream = Loader.class.getResourceAsStream(filePath);
        if (fileStream == null) {
            throw new IllegalArgumentException("The request file does not exist.");
        }
        return fileStream;
    }

    private Loader() {
    }
}
