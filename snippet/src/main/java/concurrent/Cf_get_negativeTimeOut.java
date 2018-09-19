package concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Cf_get_negativeTimeOut {
    public static void main(String[] args) {
//        getNonCompletedWithNegativeTimeOut();
//        getCompletedWithInterruptAndNegativeTimeOut();
        getNonCompletedWithInterruptAndNegativeTimeOut();
    }

    private static void getNonCompletedWithNegativeTimeOut() {
        CompletableFuture<String> completableFuture1 = new CompletableFuture<>();
        doGet(completableFuture1);
    }

    private static void getCompletedWithNegativeTimeOut() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("value");
        doGet(completableFuture);

    }

    private static void getCompletedWithInterruptAndNegativeTimeOut(){
        Thread.currentThread().interrupt();
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("value");
        doGet(completableFuture);
    }

    private static void getNonCompletedWithInterruptAndNegativeTimeOut(){
        Thread.currentThread().interrupt();
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        doGet(completableFuture);
    }

    private static void doGet(CompletableFuture<String> completableFuture) {
        try {
            String ret = completableFuture.get(-1000, TimeUnit.MILLISECONDS);
            System.out.println(ret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
