package redisson.sentinel_client;

import com.timgroup.statsd.StatsDClient;
import io.github.nov11.TimGroupStatsDClient;
import io.github.nov11.udp.UdpPipelineClient;
import org.redisson.Redisson;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * make this client works even if redis servers are down and cannot be connected
 * I'm possibly going into a wrong direction.
 *
 * by the way.
 * Trying to reproduce long response time situation when some redis server are down
 * still trying...
 */
public class SClient {
    private static final Logger logger = LoggerFactory.getLogger(SClient.class);
    private static final StatsDClient STATSD_CLIENT = new TimGroupStatsDClient("redisson-client", new UdpPipelineClient("localhost", 8125));
    private RedissonClient client;

    public SClient() {
        init();
    }

    private void init() {
        Config config = new Config();
        config.useSentinelServers()
                .addSentinelAddress("redis://localhost:5000")//, "redis://localhost:5001", "redis://localhost:5002")
                .setMasterName("mymaster")
                .setReadMode(ReadMode.SLAVE)
                .setSlaveConnectionMinimumIdleSize(0)
                .setSlaveConnectionPoolSize(3)
                .setMasterConnectionMinimumIdleSize(0)
                .setMasterConnectionPoolSize(3)
                .setIdleConnectionTimeout(1000)
                .setConnectTimeout(100)
                .setTimeout(10)
//                .setRetryAttempts(1)
                .setRetryInterval(400)
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
        SClient sClient = new SClient();

        logger.info("!!!!");
        sClient.flushDB();

        String key = "foo";
        String value = "bar";
        sClient.setValue(key, value);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    try {
                        logger.info("scheduled");
                        long start = System.currentTimeMillis();
                        String s = sClient.getValue(key);
                        STATSD_CLIENT.time("getValue", System.currentTimeMillis() - start);
                        logger.info("returned value : actual :{} expected :{}", s, value);
                    } catch (Exception e) {
                        logger.info("ex:", e);
                    }
                },
                0,
                10, TimeUnit.MILLISECONDS);
    }
}
