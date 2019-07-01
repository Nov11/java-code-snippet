package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

public class Mget {
    private static final Logger logger = LoggerFactory.getLogger(Mget.class);

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        List<String> ret = jedis.mget("foo");
        logger.info("ret : {}", ret);
    }
}
