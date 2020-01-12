package pkg;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * async interface does not timeout
 */
public class AsyncTimeOut {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTimeOut.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisClient client = RedisClientConfig.buildClient();
        StatefulRedisConnection<String, String> connection = client.connect(RedisClientConfig.redisUri);
        logger.info("mget");
        List<KeyValue<String, String>> list = connection.async().mget("foo", "bar").get();
        logger.info("return");
        for (KeyValue<String, String> kv : list) {
            logger.info("{} {}", kv.getKey(), kv.getValue());
        }
    }
}
