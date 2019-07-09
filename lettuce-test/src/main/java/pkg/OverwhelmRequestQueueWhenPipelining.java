package pkg;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverwhelmRequestQueueWhenPipelining {
    private static final Logger logger = LoggerFactory.getLogger(OverwhelmRequestQueueWhenPipelining.class);

    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create();
        ClientOptions clientOptions = ClientOptions.builder()
                .requestQueueSize(10)
                .build();
        redisClient.setOptions(
                clientOptions
        );


        StatefulRedisConnection<String, String> connection = redisClient.connect(RedisURI.create("localhost", 6379));
        connection.setAutoFlushCommands(false);
        RedisAsyncCommands<String, String> cmd = connection.async();
        for (int i = 0; i < 100; i++) {
            cmd.set(String.valueOf(i), "0");
        }

        connection.flushCommands();
        connection.setAutoFlushCommands(true);

        logger.info("done");
    }
}
