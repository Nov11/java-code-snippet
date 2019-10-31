package pkg.inheritance_cfg;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pkg.inheritance_cfg.mod1.DHolder;

public class Drive {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ChildCfg.class);
        DHolder dHolder = configApplicationContext.getBean(DHolder.class);
    }
}
