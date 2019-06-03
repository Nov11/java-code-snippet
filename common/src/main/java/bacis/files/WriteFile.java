package bacis.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class WriteFile {
    private static final Logger logger = LoggerFactory.getLogger(WriteFile.class);
    public static void main(String[] args) throws IOException {
        File file = new File("fff");
        logger.info("exists {}", file.exists());
        logger.info("delete {}", file.delete());
        logger.info("create {}", file.createNewFile());
    }
}
