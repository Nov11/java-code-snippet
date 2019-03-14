package spring_profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import spring_profile.obj.ClassA;

@Profile("default")
@Configuration
public class DefaultConfig {
    @Bean
    ClassA classA() {
        return new ClassA();
    }
}
