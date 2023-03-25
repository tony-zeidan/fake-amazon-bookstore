package fakeamazon.bookstore.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.aspectj.lang.ProceedingJoinPoint;

public class CommandRemoteCall extends HystrixCommand<String> {
    private final ProceedingJoinPoint joinPoint;
    public CommandRemoteCall(Setter config, final ProceedingJoinPoint joinPoint) {
        super(config);
        this.joinPoint = joinPoint;
    }


    @Override
    protected String run() throws Exception {
        return null;
    }
}
