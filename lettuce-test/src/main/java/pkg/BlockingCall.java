package pkg;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * blocking interface will timeout
 */
public class BlockingCall {
    private static final Logger logger = LoggerFactory.getLogger(BlockingCall.class);

    public static void main(String[] args) {
        RedisClient client = RedisClientConfig.buildClient();

        StatefulRedisConnection<String, String> connection = client.connect(new RedisURI("localhost", 6379, Duration.ofMillis(10)));
        RedisCommands<String, String> command1 = connection.sync();
        RedisCommands<String, String> command2 = connection.sync();
        logger.info("before");
        List<KeyValue<String, String>> list = connection.sync().mget("foo", "bar");
        logger.info("after");
        for (KeyValue<String, String> stringStringKeyValue : list) {
            logger.info("! {} {}", stringStringKeyValue.getKey(), stringStringKeyValue.getValue());
        }
    }
}
