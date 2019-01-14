package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class UnexpectedEndOfStream {
    private static final Logger logger = LoggerFactory.getLogger(UnexpectedEndOfStream.class);
    // this will not produce brokenpipe
    // but Unexpected end of stream.
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");
        try(Jedis jedis = jedisPool.getResource()){
            String ret = jedis.get("foo");
            logger.info("get foo: {}" , ret);
            Thread.sleep(190000);
            ret = jedis.get("bar");
            logger.info("get bar:{}", ret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
