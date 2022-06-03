package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public String readFile(String script) throws IOException, URISyntaxException
    {
        Path path = Paths.get(this.getClass().getClassLoader().getResource(script).toURI());
        return Files.readString(path);
    }
}
