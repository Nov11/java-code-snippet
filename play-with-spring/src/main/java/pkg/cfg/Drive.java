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

        Bb b = context.getBean(Bb.class);
        b.print(); // a b c
        b.printIfce();// 3 2
        b.fromCtx();


        List<Ifce> ff = (List<Ifce>) context.getBean("ifces");
        System.out.println("ff:" + ff.size());
    }
}
