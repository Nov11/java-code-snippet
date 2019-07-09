package pkg;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static pkg.RedisClientConfig.buildClient;

public class FutureChaingExecutingThread {
    private static final Logger logger = LoggerFactory.getLogger(FutureChaingExecutingThread.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisClient redisClient = buildClient();
        StatefulRedisConnection<String, String> ret = redisClient.connect(RedisURI.create("localhost", 6379));
        CompletionStage<Void> f = ret.async().mget("foo134").thenAccept(list ->
                System.out.println(list.size())
        ).exceptionally(e -> {
            logger.info("ex:", e);
            return null;
        });

        f.toCompletableFuture().get();
    }
}
