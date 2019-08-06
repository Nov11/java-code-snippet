package pkg;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClassCreation {
    private static final Logger logger = LoggerFactory.getLogger(TestClassCreation.class);

    public TestClassCreation() {
        logger.info("ctor");
    }

    @Test
    public void test1() {
        logger.info("test1");
    }

    @Test
    public void test2() {
        logger.info("test2");
    }
}
