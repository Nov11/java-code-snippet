package pkg.properties;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Driver {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        Hoder hoder = context.getBean(Hoder.class);

        System.out.println(hoder.toString());
    }
}
