package pkg;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static pkg.RedisClientConfig.buildClient;

public class Streaming {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisClient redisClient = buildClient();
        StatefulRedisConnection<String, String> connection = redisClient.connect(RedisClientConfig.redisUri);

        Map<String, String> map = new HashMap<>();
        IntStream.range(0, 10).boxed().forEach(i -> map.put(String.valueOf(i), String.valueOf(i)));
        connection.sync().hmset("kk", map);
        connection.async().hgetall((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        }, "kk").get();
    }
}
