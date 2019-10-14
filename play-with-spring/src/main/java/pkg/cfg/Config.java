package pkg.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class Config {
    @Bean
    List<String> someList(){
        return Arrays.asList("a", "b", "c");
    }
}
