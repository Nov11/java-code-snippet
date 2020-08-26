package pkg.inheritance_cfg.mod1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pkg.inheritance_cfg.Obj1;


@ComponentScan
@Configuration
public class Config {


    @Bean
    Instance instance() {
        return new Instance(1);
    }

    //this will cause circular reference error
    @Autowired
    Holder holder;
//    @Bean
//    Holder holder() {
//        return new Holder();
//    }

    @Bean
    Obj1 obj1() {
        System.err.println("cfg");
        return new Obj1(1);
    }
}
