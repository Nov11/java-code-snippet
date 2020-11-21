package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.net.URI;

public class JedisTimeouts {
    private static final Logger logger = LoggerFactory.getLogger(JedisTimeouts.class);
    private static final Jedis jedis = new Jedis(URI.create("redis://localhost:6379"), 100);

    public static void main(String[] args) {
        logger.info("before");
        String ret = jedis.setex("foo", 10, "bar");
        logger.info("after {}", ret);

        jedis.setex("foo".getBytes(), 20, new byte[0]);
    }
}
