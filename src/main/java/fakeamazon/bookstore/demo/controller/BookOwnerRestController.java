package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookOwnerRestController {

    @Autowired
    private BookRepoService bookRepoService;

    public BookOwnerRestController(BookRepoService bookRepoService) {
        this.bookRepoService = bookRepoService;
    }

    @PostMapping(path="owneractions/upload", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadHook(@RequestBody Book book) {
        Book uploaded = bookRepoService.upload(book);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded + " was created within the system.");
        }
    }

    @PatchMapping(path="owneractions/edit", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> editHook(@RequestBody @NotNull Book book, @RequestParam(value="id") String bookId) {
        Book oldBook = bookRepoService.getBookById(Long.parseLong(bookId));
        if(book.getPicture()==null) {
            book.setPicture(oldBook.getPicture());
        }
        book.setQuantity(oldBook.getQuantity());
        Book uploaded = bookRepoService.upload(book);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded + " was created within the system.");
        }
    }
}
