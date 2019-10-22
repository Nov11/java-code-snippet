package pkg.wiring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Asp {
    private static final Logger logger = LoggerFactory.getLogger(Asp.class);

    @Pointcut("execution(* pkg.wiring.A.func())")
    public void func() {
    }

    @Pointcut("execution(* pkg.wiring.A.wrapper())")
    public void wrapper() {
    }

    @Around("func()")
    public Object aroundFunc(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("bfunc");
        Object ret = joinPoint.proceed();
        logger.info("efunc");
        return ret;
    }

    @Around("wrapper()")
    public Object aroundWrapper(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("bwrapper");
        Object ret = joinPoint.proceed();
        logger.info("ewrapper");
        return ret;
    }
}
