package concurrent;

import java.util.concurrent.CompletableFuture;

/**
 * Created by c6s on 18-7-8
 */
public class ExceptionHandlingTest {
    public static void main(String[] args) {
        System.out.println("main :" + Thread.currentThread().getName());
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run : " + Thread.currentThread().getName());
                completableFuture.thenApply(s -> {
                    System.out.println("apply before: " + Thread.currentThread().getName());
                    throw new RuntimeException("in apply");
//                    System.out.println("apply after: " + Thread.currentThread().getName());
//                    return "new string";
                });
                completableFuture.complete("cccc");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread exit");
            }
        }).start();

        String s = completableFuture.join();
        System.out.println(s);
    }
}
