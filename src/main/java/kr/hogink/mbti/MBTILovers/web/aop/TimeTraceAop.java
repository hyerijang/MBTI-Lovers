package kr.hogink.mbti.MBTILovers.web.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* kr.hogink.mbti.MBTILovers.web.service..*(..))")
    public Object execute(ProceedingJoinPoint JoinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        System.out.println("START: " + JoinPoint.toString());
        try {
            return JoinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + JoinPoint.toString()+ " "+timeMs +"ms");

        }
    }
}
