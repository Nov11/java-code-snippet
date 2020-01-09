package redisson.api;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.RedisClient;
import org.redisson.client.RedisClientConfig;
import org.redisson.client.RedisConnection;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

public class Keys {
    private static final Logger logger = LoggerFactory.getLogger(Keys.class);
    RedissonClient client = Redisson.create();
    List<String> list = new ArrayList<>();

    public Keys() {
        list.add("l1");
        list.add("l2");
    }

    public static void main(String[] args) {
//        Keys keys = new Keys();
//        keys.lowerLevelClient();

        RuntimeException runtimeException = new RuntimeException("message");
        logger.info("", runtimeException);
        logger.info("{}", runtimeException.getMessage());
        logger.info("{}", runtimeException.getClass());

    }

    private void lowerLevelClient() {
        RedisClientConfig config = new RedisClientConfig();
        config.setAddress("localhost", 6379);
        RedisClient redisClient = RedisClient.create(config);
        RedisConnection redisConnection = redisClient.connect();
        String ret = redisConnection.sync(StringCodec.INSTANCE, RedisCommands.SET, "k", "v");
        logger.info("{}", ret);
    }

    private void lock() {
        RLock lock = client.getLock("lock");
        lock.lock();
        lock.unlock();
    }

    private void list() {
        RList<String> ledgerList = client.getList("ledgerList");
        ledgerList.add("e1");
        ledgerList.add("e2");
        logger.info("{}", ledgerList.get(0));
    }

    private void atomicLong() {
        RAtomicLong atomicLong = client.getAtomicLong("myAtomicLong");
        atomicLong.set(5);
        atomicLong.incrementAndGet();
    }

    private void bucketList() {
        RBucket<List<String>> bucket = client.getBucket("bucketList");
        bucket.delete();

        bucket.set(list);
        logger.info("{}", bucket.get());
    }

    private void bucketString() {
        RBucket<String> bucket = client.getBucket("bucketString");
        bucket.set("abcdefg");
        logger.info("{}", bucket.get());
    }

    private void showKeys() {
        RKeys rKeys = client.getKeys();
        rKeys.getKeys().forEach(s -> {
            logger.info("{}", s);
        });
    }
}
