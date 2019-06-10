package pkg;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**         iterate pipeline
 * 10000    450+    2200+
 * 1000     126     44
 * 100      37      9
 * 10       13      2
 *
 * performance is down by little bit when tcp no delay is enabled
 * but lowest time consumption in above situations still hold
 */
public class Pipeline {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Pipeline.class);
    private static final int ITERATION = 10000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisClient redisClient = RedisClientConfig.buildClient();
        StatefulRedisConnection<String, String> connection = redisClient.connect(RedisURI.create("localhost", 6379));
        RedisAsyncCommands<String, String> commands = connection.async();

        logger.info("iterative");
        bench(commands, Pipeline::iterative);
        logger.info("pipeline");
        bench(commands, Pipeline::pipeline);
    }

    private static List<RedisFuture<?>> pipeline(RedisAsyncCommands<String, String> commands) {
        List<RedisFuture<?>> futures = new ArrayList<>();

        commands.setAutoFlushCommands(false);
        for (int i = 0; i < ITERATION; i++) {
            futures.add(commands.setex("key" + i, 10, "value"));
        }
        //7000+ms without this flush
        commands.flushCommands();
        commands.setAutoFlushCommands(true);

        return futures;
    }

    private static List<RedisFuture<?>> iterative(RedisAsyncCommands<String, String> commands) {
        List<RedisFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < ITERATION; i++) {
            futures.add(commands.setex("key" + i, 10, "value"));
        }
        return futures;
    }

    private static void bench(RedisAsyncCommands<String, String> commands, Function<RedisAsyncCommands<String, String>, List<RedisFuture<?>>> function) throws InterruptedException, ExecutionException {
        commands.flushdb();
        long start = System.currentTimeMillis();

        List<RedisFuture<?>> futures = function.apply(commands);
        boolean result = LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futures.toArray(new RedisFuture[0]));

        logger.info("took : {} ms result : {}", System.currentTimeMillis() - start, result);
    }
}
