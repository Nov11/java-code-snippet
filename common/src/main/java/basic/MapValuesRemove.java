package basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MapValuesRemove {
    private static final Logger logger = LoggerFactory.getLogger(MapValuesRemove.class);

    public static void main(String[] args) {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "1");
        map.put(2L, "2");
        map.put(3L, "3");
        map.values().removeIf(v -> v.equals("2"));
        logger.info("map : {}", map);
    }
}
