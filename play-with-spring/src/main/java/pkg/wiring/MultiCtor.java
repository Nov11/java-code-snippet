package pkg.wiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiCtor {
    private static final Logger logger = LoggerFactory.getLogger(MultiCtor.class);
    @Autowired
    F1 f1;

    public MultiCtor() {
        logger.info("default ctor invoked");
    }

    public MultiCtor(F1 f1) {
        this.f1 = f1;
        logger.info("ctor with 1 param invoked");
    }

    public void valid() {
        logger.info("f1 is not null {}", f1 != null);
    }
}
