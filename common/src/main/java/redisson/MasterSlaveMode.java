package redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class MasterSlaveMode {
    public static void main(String[] args) {
        Set<URI> slaveAddress = new HashSet<URI>();
        slaveAddress.add(URI.create("redis://localhost:6380"));
        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress("redis://localhost:6379")
                .setSlaveAddresses(slaveAddress);
        RedissonClient client = Redisson.create(config);
    }
}
