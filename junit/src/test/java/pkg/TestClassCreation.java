package pkg;

import org.junit.Assert;
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

    @Test
    public void test3() {
        logger.info("test3_begin");
        System.out.println("test3");
        Assert.fail();
        logger.info("test3_end");
    }

    @Test
    public void test4() {
        logger.info("test4");
    }
}
