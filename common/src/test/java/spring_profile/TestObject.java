package spring_profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TestObject {
    private static final Logger logger = LoggerFactory.getLogger(TestObject.class);

    public TestObject() {
        logger.info(this.getClass().getSimpleName());
    }
}
