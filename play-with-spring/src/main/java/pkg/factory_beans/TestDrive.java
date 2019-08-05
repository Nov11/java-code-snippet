package pkg.factory_beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestDrive {
    private static final Logger logger = LoggerFactory.getLogger(TestDrive.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.refresh();

        Tool tool = context.getBean(Tool.class);
        logger.info("tool : {}", tool);

        ToolFactory toolFactory = context.getBean(ToolFactory.class);
        logger.info("toolFactory : {}", toolFactory);

        Config config = context.getBean(Config.class);
        logger.info("config : tool - {} toolFactory - {}", config.tool, config.toolFactory);
    }
}
