package pkg;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.SocketOptions;

import java.time.Duration;

public class RedisClientConfig {
    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create();
        //command time out
        redisClient.setDefaultTimeout(Duration.ofMillis(199));
        //connect timeout
        redisClient.setOptions(ClientOptions.builder().socketOptions(SocketOptions.builder().connectTimeout(Duration.ofMillis(100)).build()).build());
    }

    public static RedisClient buildClient() {
        RedisClient redisClient = RedisClient.create();
        redisClient.setDefaultTimeout(Duration.ofMillis(10));
        redisClient.setOptions(ClientOptions.builder().socketOptions(SocketOptions.builder().connectTimeout(Duration.ofMillis(10)).tcpNoDelay(true).build()).build());
        return redisClient;
    }
}
