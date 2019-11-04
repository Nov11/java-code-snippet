package pkg;

import java.util.ArrayList;
import java.util.List;

public class RollingLog {
    public static void main(String[] args) {
        while (true) {
            work();
        }
    }

    private static void work() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(String.valueOf(i));
        }

//        System.out.println(list.size());
    }
}
