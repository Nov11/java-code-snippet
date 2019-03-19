package spring_profile.obj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevClass {
    private static final Logger logger = LoggerFactory.getLogger(DevClass.class);
    public DevClass() {
        logger.info(DevClass.class.getSimpleName());
    }
}
