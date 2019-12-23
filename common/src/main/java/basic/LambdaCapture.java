package basic;

import java.util.Iterator;

public class LambdaCapture {
    public static void main(String[] args) {
        Ib ib = new Ib();
        for (Long l : ib) {
            System.out.println("bla");
        }
    }

    private static class Ib implements Iterable<Long> {

        @Override
        public Iterator<Long> iterator() {
            return new It();
        }
    }

    private static class It implements Iterator<Long> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Long next() {
            return null;
        }
    }
}
