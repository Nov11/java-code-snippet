package concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Cf_get_negativeTimeOut_interrupted {
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete(null);
        String s = completableFuture.join();
        System.out.println(s);

    }
}
