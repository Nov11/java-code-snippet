package redisson;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;

public class ReproduceUnableToConnectEx {
    public static void main(String[] args) throws InterruptedException {
        sentinelConnection();
        Thread.sleep(10000);
    }

    private static void sentinelConnection() {
        Config config = new Config();
        config.useSentinelServers()
                .setReadMode(ReadMode.SLAVE)
                .setMasterConnectionMinimumIdleSize(1)
                .setSlaveConnectionMinimumIdleSize(1)
                .setConnectTimeout(100)
                .setTimeout(200)
                .setRetryAttempts(1)
                .setMasterName("mymaster")
                .setScanInterval(60000)
                .addSentinelAddress("redis://localhost:5000")
                .addSentinelAddress("redis://localhost:5001")
                .addSentinelAddress("redis://localhost:5002");
        RedissonClient client = Redisson.create(config);

    }

    private static void readTimeOutEx() {
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379").setConnectTimeout(100).setTimeout(200).setConnectionPoolSize(1).setConnectionMinimumIdleSize(1);
        RedissonClient redissonClient = Redisson.create(config);
        RKeys rKeys = redissonClient.getKeys();
        for (String s : rKeys.getKeys()) {
            System.out.println(s);
        }
    }
}
