package basic.streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class LimitZero {
    private static final Logger logger = LoggerFactory.getLogger(LimitZero.class);

    public static void main(String[] args) {
        {
            List<Long> list = new ArrayList<>();
            list.add(1L);
            List<Long> ret = list.stream().limit(10).collect(Collectors.toList());
            logger.info("ret: {}", ret);
        }

        {
            List<Long> list = new ArrayList<>();
            list.add(1L);
            list.add(2L);
            list.add(3L);
            list.add(4L);

            List<Long> ret = list.stream().sorted(Comparator.comparing((Long v) -> v).reversed()).limit(2).collect(Collectors.toList());
            logger.info("sorted {}", ret);
        }
    }
}
