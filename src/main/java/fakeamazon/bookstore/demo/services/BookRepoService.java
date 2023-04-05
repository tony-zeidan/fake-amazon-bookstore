package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.aop.LoggedServiceOperation;
import fakeamazon.bookstore.demo.exceptions.BookRepoDownException;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookRepoService {

    private final BookRepository bookRepo;
    private boolean repoUp;

    @Autowired
    public BookRepoService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
        this.repoUp = true;
    }

    @LoggedServiceOperation
    public Book upload(Book book) {
        return bookRepo.save(book);
    }

    /**
     * Retrieves a Book from the BookRepository based on the provided ID.
     *
     * @param id the ID of the Book to retrieve
     * @return the Book with the provided ID
     */
    @LoggedServiceOperation
    public Book getBookById(Long id) {
        return bookRepo.findById(id).get();
    }

    /**
     * Service that gets All books with filtering and paging. This also contains the
     * integration of the circuit breaker.
     *
     * @param name filters the inventory based on the book's name
     * @param isbn filters the inventory based on the book's isbn
     * @param description filters the inventory based on the book's description
     * @param publisher filters the inventory based on the book's publisher
     * @param paging the current page and size that is being requested
     * @return
     */
    @CircuitBreaker(name="CircuitBreakerService", fallbackMethod = "fallback")
    public Map<String, Object> getAllBooks(String name, String isbn, String description, String publisher, Pageable paging) {
        if (!repoUp)throw new BookRepoDownException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
        Page<Book> booksPage = bookRepo.getFilterBooks(name, isbn, description, publisher, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("books", booksPage.getContent());
        response.put("currentPage", booksPage.getNumber());
        response.put("totalItems", booksPage.getTotalElements());
        response.put("totalPages", booksPage.getTotalPages());

        return response;
    }

    /**
     * Fall back method to test the circuit breaker pattern.
     * @param name filters the inventory based on the book's name
     * @param isbn filters the inventory based on the book's isbn
     * @param description filters the inventory based on the book's description
     * @param publisher filters the inventory based on the book's publisher
     * @param paging the current page and size that is being requested
     * @return The books that match the filter for the page and size
     * @param e the exception being thrown
     * @return
     */
    public Map<String, Object> fallback(String name, String isbn, String description, String publisher, Pageable paging, Exception e) {
        System.out.println(e);
        return null;
    }

    /**
     * Dummy method to show the circuit in action, where it either causes getAllBooks to work properly
     * or throw exceptions.
     */
    public void toggleRepo() {
        repoUp = !repoUp;
    }
}
