package basic.byteops;

/**
 * sign bit extension
 */
public class ByteExtension {
    public static void main(String[] args) {
        byte b = (byte)(0xff);
        long longValue = b;
        System.out.println(longValue);
        long longValue2 = b & 0xff;
        System.out.println(longValue2);
        long longValue3 = (b & 0xff) << 2;
        System.out.println(longValue3);
    }
}
