package fakeamazon.bookstore.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(LoggedServiceOperation)")
    public void logServiceOperationPointcut() {
    }

    @Pointcut("@annotation(Log)")
    public void logPointcut() {
    }

    @Before("logPointcut()")
    public void logAllMethodCallsAdvice(){

    }

    @Before("logServiceOperationPointcut()")
    public void logServiceEntry(JoinPoint point){

    }

    @Around("logServiceOperationPointcut()")
    public Object logServiceEntryExit(ProceedingJoinPoint point) throws Throwable {
        String name = point.getSignature().getDeclaringType().getSimpleName() + ":" + point.getSignature().getName();
        log.info("Service function entered: " + name);
        StopWatch watch = new StopWatch(name);
        watch.start();
        Object result = point.proceed();
        watch.stop();
        log.info("Service function exited: " + name + " the time taken was: " + watch.prettyPrint());
        return result;
    }
}
