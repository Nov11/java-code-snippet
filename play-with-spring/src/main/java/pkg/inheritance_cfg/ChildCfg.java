package pkg.inheritance_cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pkg.inheritance_cfg.mod1.Config;
import pkg.inheritance_cfg.mod1.DHolder;

@Import(Config.class)
@Configuration
public class ChildCfg {
    @Autowired
    DHolder dHolder;

    @Bean
    Obj1 obj1() {
        System.err.println("childcfg");
        return new Obj1(2);
    }
}
