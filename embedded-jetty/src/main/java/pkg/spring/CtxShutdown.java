package pkg.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * a normal spring context shutdown should print these logs
 * <p>
 * 16:54:57.179 [main] DEBUG o.s.c.e.PropertySourcesPropertyResolver:92  - Could not find key 'spring.liveBeansView.mbeanDomain' in any property source
 * 16:54:57.180 [main] INFO  pkg.spring.CtxShutdown:13  - before closing ctx
 * 16:54:57.180 [main] INFO  o.s.c.a.AnnotationConfigApplicationContext:984  - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@7e0ea639: startup date [Thu Mar 26 16:54:57 CST 2020]; root of context hierarchy
 * 16:54:57.180 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory:251  - Returning cached instance of singleton bean 'lifecycleProcessor'
 * 16:54:57.180 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory:505  - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@9660f4e: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,config,elapsingHandler]; root of factory hierarchy
 */
public class CtxShutdown {
    private static final Logger logger = LoggerFactory.getLogger(CtxShutdown.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.registerShutdownHook();
        logger.info("before closing ctx");
        context.close();
    }
}
