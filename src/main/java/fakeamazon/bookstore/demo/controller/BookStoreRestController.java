package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.services.BookRepoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;

@RestController
@RequestMapping("bookstore")
public class BookStoreRestController {

    private final ExternalAPICaller externalAPICaller;
    private final BookRepoService bookRepoService;

    @Autowired
    public BookStoreRestController(ExternalAPICaller externalAPICaller, BookRepoService bookRepoService) {
        this.externalAPICaller = externalAPICaller;
        this.bookRepoService = bookRepoService;
    }


    @GetMapping(value = "getbooks", produces = "application/json")
    @Retry(name="retryApi")
    @CircuitBreaker(name="CircuitBreakerService")
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String isbn,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") String publisher,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
//        Pageable paging = PageRequest.of(page, size);
//        Map<String, Object> response = bookRepoService.getAllBooks(name, isbn, description, publisher, paging);
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "stopRepo")
    public void stopRepo() {
        bookRepoService.toggleRepo();
    }
}
