package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.exceptions.QuantityInvalidException;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.input.templates.BookQuantityTemplate;
import fakeamazon.bookstore.demo.input.templates.CustomerUsernameTemplate;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import fakeamazon.bookstore.demo.services.BookOwnerInventoryService;
import fakeamazon.bookstore.demo.services.BookRepoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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

    @PostMapping(path = "upload", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadHook(@RequestBody Book book) {
        Book uploaded = bookRepoService.upload(book);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded + " was created within the system.");
        }
    }

    /**
     * Handle administrator request to increment or decrement the inventory value of a book.
     *
     * @param newBook The book requested to update inventory
     * @return The book that has inventory updated
     */
    @PatchMapping(path = "inventory", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> updateInventory(@RequestBody BookQuantityTemplate newBook) {
        //All that matters is to find the book via the provided ID and change to the new quantity
        try {
            Optional<Book> updatedBook = inventoryService.updateQuantity(newBook.getId(), newBook.getQuantity());
            return ResponseEntity.of(updatedBook);
        } catch (QuantityInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("ErrorResponse", e.getMessage()).body(null);
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


