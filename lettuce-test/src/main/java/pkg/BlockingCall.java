package pkg;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class BlockingCall {
    private static final Logger logger = LoggerFactory.getLogger(BlockingCall.class);

    public static void main(String[] args) {
        RedisClient client = RedisClientConfig.buildClient();

        StatefulRedisConnection<String, String> connection = client.connect(new RedisURI("localhost", 6379, Duration.ofMillis(10)));

        List<KeyValue<String, String>> list = connection.sync().mget("foo", "bar");
        for (KeyValue<String, String> stringStringKeyValue : list) {
            logger.info("! {} {}", stringStringKeyValue.getKey(), stringStringKeyValue.getValue());
        }
    }
}
