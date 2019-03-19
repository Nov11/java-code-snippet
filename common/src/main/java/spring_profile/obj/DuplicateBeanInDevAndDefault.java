package spring_profile.obj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicateBeanInDevAndDefault {
    private static final Logger logger = LoggerFactory.getLogger(DuplicateBeanInDevAndDefault.class);

    public DuplicateBeanInDevAndDefault() {
        logger.info(DuplicateBeanInDevAndDefault.class.getTypeName());
    }
}
