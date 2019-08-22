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

public class MannulAop {

    private static final Logger logger = LoggerFactory.getLogger(MannulAop.class);

    @Aspect
    static class Asppppp {
        @Pointcut("execution (* pkg.aop.Tool.foo(..))")
        public void foo() {
        }

        @Pointcut("@annotation(pkg.aop.wow)")
        public void bar() {
        }

        @Around("foo()")
        public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            System.out.println("1");
            Object ret = proceedingJoinPoint.proceed();
            System.out.println("2");
            return ret;
        }

//        @Around("bar()")
//        public Object aroundMethodBar(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//            System.out.println("1");
//            Signature signature = proceedingJoinPoint.getSignature();
//            MethodSignature methodSignature = (MethodSignature) signature;
//            Method method = methodSignature.getMethod();
//            Annotation[] annotations = method.getDeclaredAnnotations();
//            Object ret = proceedingJoinPoint.proceed();
//            System.out.println("2");
//            return ret;
//        }

        @Around("bar()")
        public Object aroundMethodBar(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            System.out.println("1");
            Signature signature = proceedingJoinPoint.getSignature();
            String methodName = signature.getName();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Annotation annotations = method.getAnnotation(wow.class);
            logger.info("0 method:{} annotation : {}", method, annotations);

            if (method.getDeclaringClass().isInterface()) {
                Method method1 = proceedingJoinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
                Annotation annotations1 = method1.getAnnotation(wow.class);
                logger.info("1 method:{} annotation : {}", method, annotations1);
            }

            Object ret = proceedingJoinPoint.proceed();
            System.out.println("2");
            return ret;
        }
    }

    public static void main(String[] args) {
        Asppppp asp = new Asppppp();
        ToolInterface tool = new Tool();
        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(tool);
        aspectJProxyFactory.addAspect(asp);
        ToolInterface tool1 = aspectJProxyFactory.getProxy();

        tool1.bar();
    }
}
