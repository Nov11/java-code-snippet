package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

public class Distrilock {
    private static final Logger logger = LoggerFactory.getLogger(Distrilock.class);
    private JedisPool jedisPool;

    public Distrilock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public boolean lock(String lockName, String randomValue, int millis) {
        try (Jedis jedis = jedisPool.getResource()) {
            String ret = jedis.set(lockName, randomValue, "NX", "PX", millis);
            return "OK".equals(ret);
        } catch (Exception e) {
            logger.error("ex:", e);
            return false;
        }
    }

    public boolean unlock(String lockName, String randomValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        try (Jedis jedis = jedisPool.getResource()) {
            Object ret = jedis.eval(script, Collections.singletonList(lockName), Collections.singletonList(randomValue));
            return ret instanceof Long && ((Long) ret) == 1L;
        } catch (Exception e) {
            logger.error("ex:", e);
            return false;
        }
    }

    public static void main(String[] args) {
        Distrilock distrilock = new Distrilock(new JedisPool());
        String lockName = "12345";
        String randomValue = "abcde";
        boolean lock = distrilock.lock(lockName, randomValue, 4000);
        boolean unlock = distrilock.unlock(lockName, randomValue);
        logger.info("lock : {} unlock : {}", lock, unlock);
    }
}
