package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexClassInitialization {
    private static final Logger logger = LoggerFactory.getLogger(ComplexClassInitialization.class);
    public Initialized a = new Initialized();

    public ComplexClassInitialization() {
        String ret = a.returnSomeThing();
        logger.info("ret : {}", ret);
    }
}
