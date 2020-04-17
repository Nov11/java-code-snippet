package pkg.wiring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Drive {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        A a = context.getBean(A.class);
        a.wrapper();

        MultiCtor multiCtor = context.getBean(MultiCtor.class);
        multiCtor.valid();
    }
}
