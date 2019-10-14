package basic.memoryleak;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JhatDiff {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("press to allocate");
            String ret = scanner.next();
            if (ret.equals("q")) {
                break;
            }
            list.add(i++);
        }
    }
}
