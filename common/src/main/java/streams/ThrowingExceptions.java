package streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThrowingExceptions {
    private static final Logger logger = LoggerFactory.getLogger(ThrowingExceptions.class);

    private static int foo(int i) {
        throw new RuntimeException("blabla");
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);

        try {
            list.forEach(ThrowingExceptions::foo);
        } catch (Exception e) {
            logger.error("ex ", e);
        }

        List<Integer> ret =
                list.stream().map(i -> foo(i)).collect(Collectors.toList());

    }
}
