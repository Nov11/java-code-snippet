package pkg.annotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoJsonProperty {
    private static final Logger logger = LoggerFactory.getLogger(NoJsonProperty.class);

    private static class PlainOldObject {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


    public static void main(String[] args) throws JsonProcessingException {
        PlainOldObject plainOldObject = new PlainOldObject();
        plainOldObject.setId(12345);

        ObjectMapper objectMapper = new ObjectMapper();
        logger.info("output : {}", objectMapper.writeValueAsString(plainOldObject));
    }
}
