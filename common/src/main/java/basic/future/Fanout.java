package basic.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * this works
 */
public class Fanout {
    private static final Logger logger = LoggerFactory.getLogger(Fanout.class);

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.thenApplyAsync(s -> {logger.info("1 {}", s);return s;}).thenAccept(s->logger.info("1 s{}", s));
        completableFuture.thenApplyAsync(s -> {logger.info("2 {}", s);return s;}).thenAccept(s->logger.info("2 s{}", s));
        completableFuture.thenApplyAsync(s -> {logger.info("3 {}", s);return s;}).thenAccept(s->logger.info("3 s{}", s));
        completableFuture.thenApplyAsync(s -> {logger.info("4 {}", s);return s;}).thenAccept(s->logger.info("4 s{}", s));
        completableFuture.complete("foo");
        Thread.sleep(100);
    }
}
