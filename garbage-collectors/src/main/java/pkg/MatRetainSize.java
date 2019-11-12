package pkg;

import java.util.ArrayList;
import java.util.List;

public class MatRetainSize {
    public static void main(String[] args) throws InterruptedException {
        A a = new A();
//        B b = new B();
//        b.list = list;

        List<byte[]> list = a.list;

        for (int i = 0; i < 100000; i++) {
            list.add(new byte[1024]);
        }
        Thread.sleep(10000000);
    }

    static class A {
        List<byte[]> list = new ArrayList<>();
    }

    static class B {
        List<byte[]> list;
    }
}
