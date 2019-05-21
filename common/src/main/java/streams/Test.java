package streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        Stream<Integer> stream = list.stream().filter(i -> i > 2);

        List<Integer> largerThan6 = stream.filter(i -> i > 6).collect(Collectors.toList());
        List<Integer> largerThan3 = stream.filter(i -> i > 3).collect(Collectors.toList());

        System.out.println("larger than 6");
        largerThan6.forEach(System.out::println);

        System.out.println("larger than 3");
        largerThan3.forEach(System.out::println);
    }
}
