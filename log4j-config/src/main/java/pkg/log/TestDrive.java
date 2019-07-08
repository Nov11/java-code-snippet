package pkg.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDrive {
    private static final Logger logger = LoggerFactory.getLogger(TestDrive.class);

    public static void main(String[] args) {
        logger.info("common message line1");
        logger.info("common message line2");

        RuntimeException exception = new RuntimeException("dumb exception");
        logger.info("ex:", exception);
    }
}
