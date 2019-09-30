package streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Creation {
    private static final Logger logger = LoggerFactory.getLogger(Creation.class);

    private static <T> void show(Stream<T> stream) {
        logger.info("++++");
        stream.forEach(s -> logger.info("{}", s));
    }

    public static void main(String[] args) {
        Stream<String> empty = Stream.empty();
        show(empty);

        Stream<String> fromArray = Stream.of("a", "b", "c", "d");
        show(fromArray);

        String[] stringArray = new String[]{
                "a", "b", "c", "d"
        };
        Stream<String> fromArray2 = Arrays.stream(stringArray);
        show(fromArray2);

        Stream<String> builder = Stream.<String>builder().add("a").add("b").add("c").add("d").build();
        show(builder);

        Stream<String> generated = Stream.generate(() -> {
            return "a";
        }).limit(5);
        show(generated);

        Stream<String> generated2 = Stream.iterate("a", v -> {
            char[] c = v.toCharArray();
            c[0] = (char) (c[0] + 1);
            return new String(c);
        }).limit(10);
        show(generated2);

        IntStream intStream = IntStream.range(0, 5);
        intStream.forEach(i -> logger.info("{}", i));
        IntStream chars = "abcdefg".chars();
        chars.forEach(i -> logger.info("{}", i));
    }
}
