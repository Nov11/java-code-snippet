package redisson;

import org.redisson.Redisson;
import org.redisson.api.RBuckets;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RedissonAsyncTimeout {
    private static final Logger logger = LoggerFactory.getLogger(RedissonAsyncTimeout.class);

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setTimeout(10);
        RedissonClient client = Redisson.create(config);

        RBuckets buckets = client.getBuckets();
        Map<String, String> ret = buckets.get("foo", "bar");
        ret.forEach((k, v) -> logger.info("{} {}", k, v));
    }
}
