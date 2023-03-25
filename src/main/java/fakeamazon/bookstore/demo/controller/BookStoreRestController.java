package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.services.BookRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("bookstore")
public class BookStoreRestController {

    private final BookRepoService bookRepoService;

    @Autowired
    public BookStoreRestController(BookRepoService bookRepoService) {
        this.bookRepoService = bookRepoService;
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
        Map<String, Object> response = bookRepoService.getAllBooks(name, isbn, description, publisher, paging);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
