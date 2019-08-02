package pkg;

import io.lettuce.core.MapScanCursor;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static pkg.RedisClientConfig.buildClient;
import static pkg.RedisClientConfig.redisUri;

public class HScan {
    private static final Logger logger = LoggerFactory.getLogger(HScan.class);

    public static void main(String[] args) {
        RedisClient redisClient = buildClient();

        StatefulRedisConnection<String, String> connection = redisClient.connect(redisUri);

        Map<String, String> hash = new HashMap<>();

        for (int i = 0; i < 26; i++) {
            hash.put("K_" + i, "V_" + i);
        }


        String hkey = "hkey";
        connection.sync().hmset(hkey, hash);

        MapScanCursor<String, String> cursor = connection.sync().hscan(hkey);
        while (!cursor.isFinished()) {
            Map<String, String> map = cursor.getMap();

            if (map.isEmpty()) {
                break;
            }

            map.forEach((k, v) -> {
                logger.info("k:{} v:{}", k, v);
            });

            cursor = connection.sync().hscan(hkey, cursor);
        }
    }
}
