package basic.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class FutureGetNow {
    private static final Logger logger = LoggerFactory.getLogger(FutureGetNow.class);
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        String s = completableFuture.getNow("default");
        logger.info("{}", s);
    }
}
