package pkg.factory_beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Autowired
    Tool tool;

//    @Resource(name = "&toolFactory")
    @Autowired
    ToolFactory toolFactory;

    @Bean
    ToolFactory toolFactory() {
        return new ToolFactory();
    }
}
