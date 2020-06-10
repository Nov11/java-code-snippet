package pkg;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * async interface does not timeout
 */
public class AsyncTimeOut {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTimeOut.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisClient client = RedisClientConfig.buildClient();
//        StatefulRedisConnection<String, String> connection = client.connect(RedisURI.create("redis://localhost"));
        StatefulRedisConnection<String, String> connection = client.connect(new RedisURI("localhost", 6379, Duration.ofMillis(10)));
        logger.info("mget");
//        List<KeyValue<String, String>> list =
                connection.async().mget("foo", "bar")
                .thenApply(v->{
                    logger.info("{}", v );
                    return v;
                });
        logger.info("return");
//        for (KeyValue<String, String> kv : list) {
//            logger.info("{} {}", kv.getKey(), kv.getValueOrElse("null"));
//        }
        Thread.sleep(Long.MAX_VALUE);
    }
}
