package pkg.inheritance_cfg.mod1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class Config {

    @Bean
    Instance instance() {
        return new Instance(1);
    }

    @Autowired
    Holder holder;
//    @Bean
//    Holder holder() {
//        return new Holder();
//    }
}
