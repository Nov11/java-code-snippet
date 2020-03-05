package pkg.basic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ReadObjects {
    private static final Logger logger = LoggerFactory.getLogger(ReadObjects.class);

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(ReadObjects.class.getClassLoader().getResourceAsStream("file.json"));
        for (JsonNode tmp : root) {
            logger.info("tmp : {}", tmp);
        }

        logger.info("==");
        logger.info("value : k1 : {}", root.get("k1"));
    }
}
