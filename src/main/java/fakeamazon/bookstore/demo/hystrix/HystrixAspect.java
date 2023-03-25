package fakeamazon.bookstore.demo.hystrix;

import com.netflix.hystrix.*;
import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class HystrixAspect {

    private HystrixCommand.Setter config;
    private HystrixCommandProperties.Setter commandProperties;
    private HystrixThreadPoolProperties.Setter threadPoolProperties;

    @Value("${remoteservice.command.execution.timeout}")
    private int executionTimeout;

    @Value("${remoteservice.command.sleepwindow}")
    private int sleepWindow;

    @Value("${remoteservice.command.threadpool.maxsize}")
    private int maxThreadCount;

    @Value("${remoteservice.command.threadpool.coresize}")
    private int coreThreadCount;

    @Value("${remoteservice.command.task.queue.size}")
    private int queueCount;

    @Value("${remoteservice.command.group.key}")
    private String groupKey;

    @Value("${remoteservice.command.key}")
    private String key;


    @Around("@annotation(fakeamazon.bookstore.demo.hystrix.HystrixCircuitBreaker)")
    public Object circuitBreakerAround(final ProceedingJoinPoint aJoinPoint) {
        return new CommandRemoteCall(config, aJoinPoint).execute();
    }

    @PostConstruct
    private void setup() {
        this.config = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey));
        this.config = config.andCommandKey(HystrixCommandKey.Factory.asKey(key));

        this.commandProperties = HystrixCommandProperties.Setter();
        this.commandProperties.withExecutionTimeoutInMilliseconds(executionTimeout);
        this.commandProperties.withCircuitBreakerSleepWindowInMilliseconds(sleepWindow);

        this.threadPoolProperties = HystrixThreadPoolProperties.Setter();
        this.threadPoolProperties.withMaxQueueSize(maxThreadCount).withCoreSize(coreThreadCount).withMaxQueueSize(queueCount);

        this.config.andCommandPropertiesDefaults(commandProperties);
        this.config.andThreadPoolPropertiesDefaults(threadPoolProperties);
    }

}