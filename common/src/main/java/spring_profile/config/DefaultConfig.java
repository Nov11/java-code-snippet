package spring_profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import spring_profile.obj.ClassA;
import spring_profile.obj.DefaultClass;
import spring_profile.obj.DuplicateBeanInDevAndDefault;

@Profile("default")
@Configuration
public class DefaultConfig {
    @Bean
    ClassA classA() {
        return new ClassA();
    }

    @Bean
    DefaultClass defaultClass() {
        return new DefaultClass();
    }

    @Bean
    DuplicateBeanInDevAndDefault duplicateBeanInDevAndDefault(){
        return new DuplicateBeanInDevAndDefault();
    }
}
