package bacis;

/**
 * ordering static fields init from different classes
 *
 * this scenario shows up when aggregating values from different classes into one singe instance.
 */
public class StaticTest {
    public static void main(String[] args) {
//        StaticInit1 staticInit1 = new StaticInit1();
        System.out.println(StaticInit2.get());
    }
}
