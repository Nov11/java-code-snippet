package redisson.sentinel_client;

import org.redisson.Redisson;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SClient {
    private static final Logger logger = LoggerFactory.getLogger(SClient.class);
    private RedissonClient client;

    public SClient() {
        init();
    }

    private void init() {
        Config config = new Config();
        config.useSentinelServers()
                .addSentinelAddress("redis://localhost:5000", "redis://localhost:5001", "redis://localhost:5002")
                .setMasterName("mymaster")
                .setReadMode(ReadMode.SLAVE)
                .setSlaveConnectionMinimumIdleSize(1)
                .setSlaveConnectionPoolSize(3)
                .setMasterConnectionMinimumIdleSize(1)
                .setMasterConnectionPoolSize(3)
                .setIdleConnectionTimeout(1000)
                .setConnectTimeout(100)
                .setTimeout(10)
                .setRetryAttempts(1)
                .setRetryInterval(100)
                .setKeepAlive(true)
                .setTcpNoDelay(true);
        client = Redisson.create(config);
    }

    public byte[] getBytes(String key) {
        RBinaryStream binaryStream = client.getBinaryStream(key);
        byte[] result = binaryStream.get();
        return result;
    }

    public String getValue(String key) {
        RBucket<String> bucket = client.getBucket(key);
        return bucket.get();
    }

    public void setValue(String key, String value) {
        RBucket<String> bucket = client.getBucket(key);
        bucket.set(value);
    }

    public void flushDB() {
        RKeys rKeys = client.getKeys();
        rKeys.flushdb();
    }

    public static void main(String[] args) {
//        SClient sClient = new SClient();
//        sClient.flushDB();
//
//        String key = "foo";
//        String value = "bar";
//        sClient.setValue(key, value);
//        logger.info("returned value : actual :{} expected :{}", sClient.getValue(key), value);
        System.out.println(System.currentTimeMillis() / 1000);
    }
}
