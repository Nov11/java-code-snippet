package spring_profile.obj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultClass {
    private static final Logger logger = LoggerFactory.getLogger(DefaultClass.class);
    public DefaultClass() {
        logger.info(DefaultClass.class.getSimpleName());
    }
}
