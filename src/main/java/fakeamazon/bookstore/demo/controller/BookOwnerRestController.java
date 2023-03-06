package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookOwnerRestController {

    @Autowired
    private BookOwnerUploadService uploadService;

    public BookOwnerRestController(BookOwnerUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping(path="owneractions/upload", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadHook(@RequestBody PartialBookBody book) {
        System.out.println(book.getName());
        Book uploaded = uploadService.upload(book);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            System.out.println(uploaded);
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded + " was created within the system.");
        }
    }
}
