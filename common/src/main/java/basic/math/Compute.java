//package basic.math;
//
//import static com.sun.xml.internal.fastinfoset.util.ValueArray.MAXIMUM_CAPACITY;
//
//public class Compute {
//    static final int tableSizeFor(int cap) {
//        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
//        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
//    }
//    public static void main(String[] args) {
//        System.out.println(tableSizeFor(10));
//    }
//}
