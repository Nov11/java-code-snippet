package pkg.inheritance_cfg;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pkg.inheritance_cfg.mod1.DHolder;
import pkg.inheritance_cfg.mod1.Instance;

public class Drive {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ChildCfg.class);
        DHolder dHolder = configApplicationContext.getBean(DHolder.class);
        Instance ins = dHolder.getInstance();
        System.out.println(ins != null);
        Obj1 ret = (Obj1) configApplicationContext.getBean("obj1");
        System.out.println(ret.i);
    }
}