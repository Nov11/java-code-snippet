package redisson;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * specify a small tcp-backlog like 1, use debug sleep to stopi, then connect the redis instance with redisson
 * the second tcp connection will be not placed into established connection queue
 * and no syn send back which result in client time out
 * redisson will throw server not reachable exception
 */
public class ReproduceUnableToConnectEx {
    private static final Logger logger = LoggerFactory.getLogger(ReproduceUnableToConnectEx.class);

    public static void main(String[] args) throws InterruptedException {
//        readTimeOutEx();
//        for (int i = 0; i < 10; i++) {
//            sentinelConnection();
//            logger.info("finished : {}", i);
//        }
//        Thread.sleep(10000);

        readTimeOutEx();
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
        config.useSingleServer().setAddress("redis://127.0.0.1:7000").setConnectTimeout(100).setTimeout(200).setConnectionPoolSize(1).setConnectionMinimumIdleSize(1);
        RedissonClient redissonClient = Redisson.create(config);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(() -> {
            RKeys rKeys = redissonClient.getKeys();
            for (String s : rKeys.getKeys()) {
                System.out.println(s);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }
}
