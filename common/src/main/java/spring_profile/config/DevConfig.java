package spring_profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import spring_profile.obj.ClassB;
import spring_profile.obj.DevClass;
import spring_profile.obj.DuplicateBeanInDevAndDefault;

@Profile("dev")
@Configuration
public class DevConfig {
    @Bean
    ClassB classB() {
        return new ClassB();
    }

    @Bean
    DevClass devClass(){
        return new DevClass();
    }

    @Bean
    DuplicateBeanInDevAndDefault duplicateBeanInDevAndDefault(){
        return new DuplicateBeanInDevAndDefault();
    }
}
