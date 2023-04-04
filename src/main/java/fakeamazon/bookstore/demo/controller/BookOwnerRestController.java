package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.input.templates.CustomerUsernameTemplate;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.input.templates.BookQuantityTemplate;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.repository.BookRepository;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import fakeamazon.bookstore.demo.services.BookOwnerInventoryService;
import fakeamazon.bookstore.demo.services.BookRepoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("owneractions")
public class BookOwnerRestController {

    private final BookOwnerInventoryService inventoryService;
    private final BookRepoService bookRepoService;
    private final CustomerRepository customerRepository;

    @Autowired
    public BookOwnerRestController(BookRepoService bookRepoService, BookOwnerInventoryService inventoryService, CustomerRepository customerRepository) {
        this.bookRepoService = bookRepoService;
        this.inventoryService = inventoryService;
        this.customerRepository = customerRepository;
    }

    /**
     * Endpoint for the uploading of a book.
     *
     * @param book The book to add and all of it's details
     * @return CREATED if book was created otherwise FORBIDDEN
     */
    @PostMapping(path = "upload", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadHook(@RequestBody Book book) {
        Book uploaded = bookRepoService.upload(book);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded + " was created within the system.");
        }
    }

    @PatchMapping(path = "inventory", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> updateInventory(@RequestBody BookQuantityTemplate newBook) {
        //All that matters is to find the book via the provided ID and change to the new quantity
        try {
            Optional<Book> updatedBook = inventoryService.updateQuantity(newBook.getId(), newBook.getQuantity());
            return ResponseEntity.of(updatedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping(path = "edit", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> editHook(@RequestBody @NotNull Book book, @RequestParam(value = "id") String bookId) {
        Book oldBook = bookRepoService.getBookById(Long.parseLong(bookId));
        if (book.getPicture() == null) {
            book.setPicture(oldBook.getPicture());
        }
        book.setQuantity(oldBook.getQuantity());
        Book uploaded = bookRepoService.upload(book);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return ResponseEntity.ok().body(uploaded);
        }
    }

    @GetMapping(path = "usernamelist")
    public ResponseEntity<List<CustomerUsernameTemplate>>userList() {
        // only getting the username attribute from the customers, who have actual purchase history.
        List<CustomerUsernameTemplate> customersList = customerRepository.findCustomersWithPurchaseItems().stream().map(c -> new CustomerUsernameTemplate(c.getUsername())).collect(Collectors.toList());
            return ResponseEntity.ok().body(customersList);
        }
}


