package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/owner")
public class BookOwnerRestController {

    @Autowired
    private BookOwnerUploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadHook(@RequestBody PartialBookBody book, @RequestParam("file") MultipartFile file) {
        Book uploaded = uploadService.upload(book, file);
        if (uploaded == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The given book could not be uploaded to the server.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("The book " + uploaded.toString() + " was created within the system.");
        }
    }
}
