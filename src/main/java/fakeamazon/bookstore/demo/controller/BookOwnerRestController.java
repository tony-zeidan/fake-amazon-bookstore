package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookOwnerRestController {
    @PutMapping(path="owneractions/inventory", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> updateInventory(@RequestBody Book newBook){
        Long newId = newBook.getId();

//        Integer newQuantity = newBook.get
        Integer newQuantity = 1;

        return ResponseEntity.status(HttpStatus.OK).body(newBook);
    }
}
