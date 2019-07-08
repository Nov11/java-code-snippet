package redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Collections;
import java.util.HashSet;

public class MasterSlaveMode {
    public static void main(String[] args) {
        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress("redis://localhost:6379")
        .setSlaveAddresses(new HashSet<>(Collections.singletonList("redis://localhost:6380")));
        RedissonClient client = Redisson.create(config);
    }
}
