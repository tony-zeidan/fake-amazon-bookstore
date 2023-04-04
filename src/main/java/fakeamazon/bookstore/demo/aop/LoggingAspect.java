package fakeamazon.bookstore.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Aspect for logging the entry and exit of service functions.
 */
@Component
@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Pointcut for any loggable service function.
     */
    @Pointcut("@annotation(LoggedServiceOperation)")
    public void logServiceOperationPointcut() {
    }

    /**
     * Attempt to log entry and exit from any function annotated with @LoggedServiceOperation.
     *
     * @param point The JointPoint of the function (preceding)
     * @return The return value of the function being annotated
     * @throws Throwable If for some reason the JointPoint can't be walked through
     */
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
