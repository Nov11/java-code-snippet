package pkg.cfg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Bb {
    private static final Logger logger = LoggerFactory.getLogger(Bb.class);

    @Autowired
    List<String> someList;

    @Autowired
    List<Ifce> ifces;

    @Autowired
    ApplicationContext context;

    public void print() {
        logger.info("someList len: {}", someList);
    }

    public void printIfce() {
        logger.info("ifces len:{}", ifces == null ? -1 : ifces.size());
    }

    public void fromCtx() {
        List<Ifce> list = (List<Ifce>) context.getBean("ifces");
        logger.info("from context list len : {}", list.size());
    }
}
