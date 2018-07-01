package java8inaction.chapter11_completablefuture;

/**
 * Created by c6s on 18-7-1
 */
public class Util {
    static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
