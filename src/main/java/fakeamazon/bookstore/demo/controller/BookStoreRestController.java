package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bookstore")
public class BookStoreRestController {

    private final BookRepository bookRepository;

    @Autowired
    public BookStoreRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "getbooks", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String isbn,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") String publisher,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {


        Pageable paging = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.getFilterBooks(name, isbn, description, publisher, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("books", booksPage.getContent());
        response.put("currentPage", booksPage.getNumber());
        response.put("totalItems", booksPage.getTotalElements());
        response.put("totalPages", booksPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
