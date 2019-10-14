package pkg.cfg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Drive {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        List<String> list = (List<String>) context.getBean("someList");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
