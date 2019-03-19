package spring_profile.obj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClassA {
    private static final Logger logger = LoggerFactory.getLogger(ClassA.class);
    public ClassA() {
        logger.info(ClassA.class.getSimpleName());
    }
}
