package basic.arrayhash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ArrayHashTest {
    private static final Logger logger = LoggerFactory.getLogger(ArrayHashTest.class);

    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4};
        Integer[] b = {1, 2, 3, 4};
        logger.info("a hash : {} deepHash : {}", a.hashCode(), Arrays.deepHashCode(a));
        logger.info("b hash : {} deepHash : {}", b.hashCode(), Arrays.deepHashCode(b));
    }
}
