package basic.containers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapValuePloy {
    private static final Logger logger = LoggerFactory.getLogger(MapValuePloy.class);

    public static void main(String[] args) {
        {
            Map<Object, Object> map = new HashMap<>();
            map.put("ss", new Object());
            map.put("s2", new Integer(1));
//            Map<String, String> map1 = map;
        }
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        cache.put("k1", new Object());
        cache.put("k2", new Object());
        ImmutableMap map = cache.getAllPresent(Arrays.asList("k1", "k2"));
        logger.info("{}", map);
        {
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("1", "2");
            stringMap.put("2", "3");
            stringMap.put("3", "4");
            logger.info("before {}", stringMap);
            stringMap.values().removeIf(s -> s.equals("2"));
            logger.info("after {}", stringMap);
        }
    }
}
