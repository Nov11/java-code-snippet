package spring_profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_profile.config.DefaultConfig;
import spring_profile.config.DevConfig;
import spring_profile.config.NormalConfig;

public class DriveDevOnly {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("dev");
        ctx.register(DefaultConfig.class, DevConfig.class, NormalConfig.class);
        ctx.refresh();

        NormalConfig normalConfig = ctx.getBean(NormalConfig.class);
        normalConfig.getActiveProfiles();
    }
}
