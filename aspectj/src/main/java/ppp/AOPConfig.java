package ppp;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import pkg.OneStruct;

@Aspect
public class AOPConfig {

    @Pointcut("execution(* pkg.OneStruct.OneStructStandardScheme.write(..))")
    public void writeMethod() {
    }

    @Around("writeMethod()")
    public Object wrapper(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("wrapper");

        Object[] parameters = joinPoint.getArgs();
        TProtocol protocol = (TProtocol)parameters[0];
        OneStruct oneStruct = (OneStruct)parameters[1];

        if (protocol instanceof TBinaryProtocol) {
            System.out.println("binary protocal");
        }

        return joinPoint.proceed();
    }
}
