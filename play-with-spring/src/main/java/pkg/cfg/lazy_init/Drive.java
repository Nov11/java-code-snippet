package pkg.cfg.lazy_init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

public class Drive {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(LazyConfig.class);

    }
}
