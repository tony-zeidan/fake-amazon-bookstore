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

    private final BookRepoService bookRepoService;

    @Autowired
    public BookStoreRestController(BookRepoService bookRepoService) {
        this.bookRepoService = bookRepoService;
    }


    /**
     * Gets all books from the inventory allowing for filtering and paging.
     *
     * @param name filters the inventory based on the book's name
     * @param isbn filters the inventory based on the book's isbn
     * @param description filters the inventory based on the book's description
     * @param publisher filters the inventory based on the book's publisher
     * @param page the current page that is being requested
     * @param size the number of books that should be returned.
     * @return The books that match the filter for the page and size
     */
    @GetMapping(value = "getbooks", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String isbn,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") String publisher,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        Map<String, Object> response = bookRepoService.getAllBooks(name, isbn, description, publisher, paging);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "toggleRepo")
    public void toggleRepo() {
        bookRepoService.toggleRepo();
    }
}
