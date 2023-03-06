package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class BookOwnerRestController {

    @Autowired
    private BookOwnerUploadService uploadService;

    public BookOwnerRestController(BookOwnerUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("test")
    public String getHook() {
        return "<h1> HELLO </h1>";
    }

    @PostMapping("tester")
    public String postHook() {
        return "<h1> HELLO </h1>";
    }

    @PostMapping(path="upload", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadHook(@RequestPart("bookdetails") PartialBookBody book, @RequestPart("bookimage") MultipartFile file) {
        System.out.println(book);
        Book uploaded = uploadService.upload(book, file);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded.toString() + " was created within the system.");
        }
    }
}
