package fakeamazon.bookstore.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class BookStoreController {

    public BookStoreController() {
    }

    private BookRepository bookRepository;

    @Autowired
    public BookStoreController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/getAllBooks", produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> lst = new LinkedList<>();
        lst.add(new Book("name1", "desc1"));
        lst.add(new Book("name2","desc2"));
        return ResponseEntity.ok(lst);
        //return ResponseEntity.ok((List<Book>)bookRepository.findAll());
    }
}
