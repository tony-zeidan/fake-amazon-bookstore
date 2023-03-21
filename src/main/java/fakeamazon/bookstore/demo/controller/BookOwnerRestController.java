package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.input.templates.BookQuantityTemplate;
import fakeamazon.bookstore.demo.repository.BookRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("owneractions")
public class BookOwnerRestController {

    private final BookOwnerInventoryService inventoryService;
    private final BookRepoService bookRepoService;
    private final BookRepository bookRepository;

    @Autowired
    public BookOwnerRestController(BookRepoService bookRepoService, BookOwnerInventoryService inventoryService, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.bookRepoService = bookRepoService;
        this.inventoryService = inventoryService;
    }

    @PostMapping(path="upload", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadHook(@RequestBody Book book) {
        Book uploaded = bookRepoService.upload(book);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded + " was created within the system.");
        }
    }

    @PatchMapping (path="inventory", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> updateInventory(@RequestBody BookQuantityTemplate newBook){
        //All that matters is to find the book via the provided ID and change to the new quantity
        try{
            Optional<Book> updatedBook = inventoryService.updateQuantity(newBook.getId(), newBook.getQuantity());
            return ResponseEntity.of(updatedBook);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping(path="edit", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> editHook(@RequestBody @NotNull Book book, @RequestParam(value="id") String bookId) {
        Book oldBook = bookRepoService.getBookById(Long.parseLong(bookId));
        if(book.getPicture()==null) {
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

    @GetMapping(value = "getAllBooksPage", produces = "application/json")
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

    @GetMapping(value = "/getAllBooks", produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }
}
