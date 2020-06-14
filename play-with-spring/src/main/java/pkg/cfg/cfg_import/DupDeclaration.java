package pkg.cfg.cfg_import;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public class DupDeclaration {

    private static interface BookService {
    }

    private static class BookServiceImpl implements BookService {
    }


    private static interface AudioBookService {
    }

    private static class AudioBookServiceImpl implements AudioBookService {

    }

    @Configuration
    @Import({SpringBeansConfig.class})
    public static class SpringMainConfig {
        @Bean
        public BookService bookServiceGenerator() {
            return new BookServiceImpl();
        }
    }

    @Configuration
    public static class SpringBeansConfig {
        @Bean
        public AudioBookService bookServiceGenerator() {
            return new AudioBookServiceImpl();
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringMainConfig.class);
        AudioBookService audioBookService = context.getBean(AudioBookService.class);
    }
}
