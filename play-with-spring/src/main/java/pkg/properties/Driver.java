package pkg.properties;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Driver {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        PropertyConfigHolder propertyConfigHolder = configApplicationContext.getBean(PropertyConfigHolder.class);

        System.out.println(propertyConfigHolder.toString());
    }
}
