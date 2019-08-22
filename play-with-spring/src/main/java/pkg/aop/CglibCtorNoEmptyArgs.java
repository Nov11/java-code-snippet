package pkg.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CglibCtorNoEmptyArgs {

    private static final Logger logger = LoggerFactory.getLogger(CglibCtorNoEmptyArgs.class);

    @Aspect
    static class Asppppp {
        @Pointcut("@annotation(pkg.aop.hitSound)")
        public void hitSound() {
        }

        @Around("hitSound()")
        public Object aroundMethodBar(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Annotation annotations = method.getAnnotation(wow.class);
            logger.info("0 method:{} annotation : {}", method, annotations);
            Object ret = proceedingJoinPoint.proceed();
            return ret;
        }
    }

    public static void main(String[] args) {
        Asppppp asp = new Asppppp();
        Hammer hammer = new Hammer("blabla");
        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(hammer);
        aspectJProxyFactory.addAspect(asp);
        Hammer hammer2 = aspectJProxyFactory.getProxy();

        hammer2.hit();
    }
}
