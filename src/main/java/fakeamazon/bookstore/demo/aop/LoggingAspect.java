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

    @AfterThrowing(pointcut="logPointcut()", throwing="e")
    public void logAfterThrowing(JoinPoint point, Throwable e) {
        log.error(point.getSignature().getName() + " had an error " + e.getCause() + e.getMessage());
    }

    @Before("logServiceOperationPointcut()")
    public void logServiceEntry(JoinPoint point){

    }

    @Around("logServiceOperationPointcut()")
    public void logServiceEntryExit(ProceedingJoinPoint point){
        String name = point.getSignature().getDeclaringType().getSimpleName() + ":" + point.getSignature().getName();
        log.info("Service function entered: " + name);
        StopWatch watch = new StopWatch(name);
        watch.start();
        try {
            point.proceed();
        } catch (Throwable e) {
            log.info("Error with logging service.");
            watch.stop();
            return;
        }
        watch.stop();
        log.info("Service function exited: " + name + " the time taken was: " + watch.prettyPrint());
    }
}
