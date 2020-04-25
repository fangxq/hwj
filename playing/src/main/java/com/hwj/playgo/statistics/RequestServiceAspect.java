package com.hwj.playgo.statistics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author holder
 * @date 2020/04/25
 */
@Aspect
@Component
public class RequestServiceAspect {

    @Around("execution(* com.hwj.playgo.strategy.RequestStrategy.execute())")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        try {
            long start = System.currentTimeMillis();
            Object obj = proceedingJoinPoint.proceed();
            System.out.print("*******************");
            System.out.println(proceedingJoinPoint.getSignature()+","+(System.currentTimeMillis() - start));
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


}
