package pkg.cfg.lazy_init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class LazyConfig {
    @Bean
    ClassA classA() {
        return new ClassA();
    }

    @Bean
    @Lazy
    ClassB classB() {
        return new ClassB();
    }
}
