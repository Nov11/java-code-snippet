package pkg;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * lettuce write:
 * blocking interface will timeout
 * async won't
 */
public class BlockingWrite {
    private static final Logger logger = LoggerFactory.getLogger(BlockingWrite.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisClient client = RedisClientConfig.buildClient();

        StatefulRedisConnection<String, String> connection = client.connect(new RedisURI("localhost", 6379, Duration.ofMillis(10)));
        logger.info("before");
//        String ret = connection.async().setex("foo", 10, "bar").get();
        String ret = connection.sync().setex("foo", 10, "bar");
        logger.info("after");
        logger.info("! result : {} ", ret);
    }
}
