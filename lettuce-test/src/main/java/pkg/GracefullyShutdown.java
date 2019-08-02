package pkg;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulConnection;

public class GracefullyShutdown {
    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create(RedisURI.create("redis://localhost:6379"));

        StatefulConnection<String, String> connection = redisClient.connect();

        connection.close();

        redisClient.shutdown();
    }
}
