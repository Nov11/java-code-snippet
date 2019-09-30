package streams;

import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Reduction {
    public static void main(String[] args) {
        OptionalInt r1 = IntStream.range(0, 3).reduce(Integer::sum);
        System.out.println(r1);

        int r2 = IntStream.range(0, 3).reduce(1, Integer::sum);
        System.out.println(r2);

//        int r3 = IntStream.range(0, 3).reduce(1, Integer::sum, Integer::sum);//not supported
        int r3 = Stream.of(0, 1, 2).reduce(1, Integer::sum, Integer::sum);
        System.out.println(r3);

        int r4 = Stream.of(0, 1, 2).parallel().reduce(1, Integer::sum, Integer::sum);
        System.out.println(r4);
    }
}
