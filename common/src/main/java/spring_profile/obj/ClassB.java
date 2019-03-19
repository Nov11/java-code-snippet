package spring_profile.obj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClassB {
    private static final Logger logger = LoggerFactory.getLogger(ClassB.class);
    public ClassB() {
        logger.info(ClassB.class.getSimpleName());
    }
}
