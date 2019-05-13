package pkg;

import java.util.ArrayList;
import java.util.List;

public class Allocation {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Allocation.class);
    public static void main(String[] args) {
        logger.info("start");
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            list.add(new Object());
        }

        logger.info("end");
    }
}
