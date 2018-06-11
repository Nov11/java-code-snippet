package concurrent;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureRunningThread {
    private static void longRunningWork() {
        try {
            System.out.println(Thread.currentThread() + " f before sleep " + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println(Thread.currentThread() + " f after sleep " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testCompose() {
        System.out.println(Thread.currentThread() + " 1 " + System.currentTimeMillis());
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        CompletableFuture<String> completableFuture1 =
                completableFuture.thenCompose(
                        i -> {
                            longRunningWork();
                            return CompletableFuture.supplyAsync(() -> i + " string!");
                        }
                );

        System.out.println(Thread.currentThread() + " 2 " + System.currentTimeMillis());
        Thread th = new Thread(
                () -> {
                    System.out.println(Thread.currentThread() + " complete start " + System.currentTimeMillis());
                    completableFuture.complete(11);
                    System.out.println(Thread.currentThread() + " complete end " + System.currentTimeMillis());
                }
        );
        th.start();

        System.out.println(Thread.currentThread() + " 3 " + System.currentTimeMillis());
        completableFuture1.join();
        System.out.println(Thread.currentThread() + " 4 " + System.currentTimeMillis());
    }

    private static void testThenAccept() {
        System.out.println(Thread.currentThread() + " 1 " + System.currentTimeMillis());
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        CompletableFuture<Void> cf = completableFuture.thenAccept(
                i -> longRunningWork()
        );

        System.out.println(Thread.currentThread() + " 2 " + System.currentTimeMillis());
        Thread th = new Thread(
                () -> {
                    System.out.println(Thread.currentThread() + " complete start " + System.currentTimeMillis());
                    completableFuture.complete(11);
                    System.out.println(Thread.currentThread() + " complete end " + System.currentTimeMillis());
                }
        );
        th.start();

        System.out.println(Thread.currentThread() + " 3 " + System.currentTimeMillis());
        cf.join();
        System.out.println(Thread.currentThread() + " 4 " + System.currentTimeMillis());
    }

    //functions will be run in the thread in which complete is called
    public static void main(String[] args) {
        testCompose();
        System.out.println("=========================");
        testThenAccept();
    }
}
