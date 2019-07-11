package softuni.workshop.util;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileUtil {

    String readFile(String path) throws IOException;
}
