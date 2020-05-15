package pkg.annotations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OnGetter {
    private static final Logger logger = LoggerFactory.getLogger(OnGetter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String expected = "{\"numberId\":12345}";


    private static class PlainOldObject {
        private int id;

        @JsonProperty("numberId")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @Test
    public void serializeTest() throws JsonProcessingException {
        PlainOldObject plainOldObject = new PlainOldObject();
        plainOldObject.setId(12345);


        String output = objectMapper.writeValueAsString(plainOldObject);
        logger.info("output : {}", output);
        Assert.assertEquals(expected, output);
    }

    @Test
    public void deserializeTest() throws IOException {
        PlainOldObject plainOldObject = objectMapper.readValue(expected, new TypeReference<PlainOldObject>() {
        });
        Assert.assertEquals(12345, plainOldObject.getId());
    }
}
