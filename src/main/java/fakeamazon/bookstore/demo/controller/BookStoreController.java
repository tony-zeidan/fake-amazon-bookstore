package fakeamazon.bookstore.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class BookStoreController {

    public BookStoreController() {
    }

    private BookRepository bookRepository;

    @Autowired
    public BookStoreController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }




}
