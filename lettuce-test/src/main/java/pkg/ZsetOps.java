package pkg;

import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static pkg.RedisClientConfig.buildClient;
import static pkg.RedisClientConfig.redisUri;

public class ZsetOps {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisClient redisClient = buildClient();
        StatefulRedisConnection<String, String> connection = redisClient.connect(redisUri);
        RedisAsyncCommands<String, String> async = connection.async();
        async.zadd("key", ScoredValue.from(11, Optional.of("value"))).get();
        List<String> ret = async.zrange("key", Long.MIN_VALUE, Long.MAX_VALUE).get();
        ret.forEach(System.out::println);
        Long removed = async.zremrangebyscore("key", Range.create(Long.MIN_VALUE, Long.MAX_VALUE)).get();
        System.out.println(removed);
    }
}
