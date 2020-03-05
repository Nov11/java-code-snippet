package pkg;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Spring4Test.Config.class)
public class Spring4Test {
    private static final Logger logger = LoggerFactory.getLogger(Spring4Test.class);

    @Configuration
    @EnableAspectJAutoProxy
    public static class Config {
        public Config() {
            logger.info("config ctor");
        }

        @Bean
        A a() {
            return new A();
        }

        @Bean
        DumbAspect dumbAspect() {
            return new DumbAspect();
        }
    }

    public static class A {
        public int foo(int c) {
            logger.info("input : {}", c);
            return c;
        }
    }

    @Aspect
    public static class DumbAspect {
        @Pointcut("execution (* pkg.Spring4Test.A.foo(int)) && args(bbbb55)")
        public void pointCut(int bbbb55) {
        }

        @Before("pointCut(bbbb2)")
        public void beforeMethod(int bbbb2) {
            logger.info("beforeMethod");
        }


        @AfterReturning("pointCut(bbbb)")
        public void afterReturning(int bbbb) {
            logger.info("afterReturning");
        }

        @After("pointCut(bbbb)")
        public void afterMethod(int bbbb) {
            logger.info("afterMethod");
        }

        @Around("pointCut(bbbb)")
        public Object around(ProceedingJoinPoint proceedingJoinPoint, int bbbb) throws Throwable {
            logger.info("around1");
            Object ret = proceedingJoinPoint.proceed();
            logger.info("around2");
            return ret;
        }
    }

    @Autowired
    A a;

    @Test
    public void t() {
        logger.info("t");
        a.foo(1234);
    }

//    @Test
//    public void t2() {
//        logger.info("t2");
//    }

    public Spring4Test() {
        logger.info("spring4test ctor");
    }
}
