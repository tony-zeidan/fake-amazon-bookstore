package fakeamazon.bookstore.demo.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ControllerTest {
    private RestTemplate restTemplate =  new RestTemplateBuilder().rootUri("http://localhost:9090").build();
    @GetMapping("/circuit-breaker")
    @CircuitBreaker(name = "CircuitBreakerService")
    public String circuitBreakerApi() {
        return restTemplate.getForObject("/api/external", String.class);
    }
}
