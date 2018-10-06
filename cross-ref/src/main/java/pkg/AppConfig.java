package pkg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class AppConfig {


    @Bean
    ClassB classB(){
        ClassB classB = new ClassB();
        classB.setClassA(classA);
        return classB;
    }

    @Autowired
    private ClassA classA;

    @Bean
    ClassA classA(){
        return new ClassA();
    }

}
