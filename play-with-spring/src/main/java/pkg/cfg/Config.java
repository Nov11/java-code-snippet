package pkg.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@ComponentScan
@Configuration
public class Config {
    @Bean
    List<String> someList(){
        return Arrays.asList("a", "b", "c");
    }

    @Autowired
    IfImpl1 f1;

    @Autowired
    IfImpl2 f2;

    @Bean
    List<Ifce> ifces(){
        return Arrays.asList(f1);
    }

}
