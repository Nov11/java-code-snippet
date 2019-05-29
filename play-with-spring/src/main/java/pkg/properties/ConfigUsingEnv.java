package pkg.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:Dumb.properties")
public class ConfigUsingEnv {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUsingEnv.class);
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigUsingEnv.class);
        Environment env = context.getBean(Environment.class);

        logger.error(env.getProperty("String.limit"));
        logger.error(env.getProperty("Int.limit"));
        logger.error(env.getProperty("String.limit", String.class));
        String tmp = env.getProperty("Int.limit", String.class);
        logger.error(tmp);
        logger.error("11");
        String tt = "11";
        logger.error(tt);
        String tto = new String("11");
        logger.error(tto);


        logger.error(env.getProperty("String.limit.quote"));
    }
}
